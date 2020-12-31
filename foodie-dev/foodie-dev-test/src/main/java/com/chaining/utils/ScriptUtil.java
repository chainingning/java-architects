package com.chaining.utils;

import org.apache.commons.exec.*;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 1、内嵌编译器如"PythonInterpreter"无法引用扩展包，因此推荐使用java调用控制台进程方式"Runtime.getRuntime().execCmd()"来运行脚本(shell或python)；
 * 2、因为通过java调用控制台进程方式实现，需要保证目标机器PATH路径正确配置对应编译器；
 * 3、暂时脚本执行日志只能在脚本执行结束后一次性获取，无法保证实时性；因此为确保日志实时性，可改为将脚本打印的日志存储在指定的日志文件上；
 * 4、python 异常输出优先级高于标准输出，体现在Log文件中，因此推荐通过logging方式打日志保持和异常信息一致；否则用prinf日志顺序会错乱
 * <p>
 *
 * @author lism
 * @date 2018年11月8日10:02:30
 */
public class ScriptUtil {

    /**
     * make script file
     *
     * @param scriptFileName
     * @param content
     * @throws IOException
     */
    public static void markScriptFile(String scriptFileName, String content) throws Exception {
        // make file,   filePath/gluesource/666-123456789.py
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(scriptFileName);
            fileOutputStream.write(content.getBytes("UTF-8"));
            fileOutputStream.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    /**
     * 日志文件输出方式
     * <p>
     * 优点：支持将目标数据实时输出到指定日志文件中去
     * 缺点：
     * 标准输出和错误输出优先级固定，可能和脚本中顺序不一致
     * Java无法实时获取
     *
     * @param command
     * @param scriptFile
     * @param logFile
     * @param params
     * @return
     * @throws IOException
     */
    public static int execToFile(String command, String scriptFile, String logFile, String... params) throws IOException {
        // 标准输出：print （null if watchdog timeout）
        // 错误输出：logging + 异常 （still exists if watchdog timeout）
        // 标准输入
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(logFile, true);
            PumpStreamHandler streamHandler = new PumpStreamHandler(fileOutputStream, fileOutputStream, null);
            int exitValue = execCmd(command, scriptFile, params, streamHandler);
            return exitValue;
        } catch (Exception e) {
            return -1;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static int execCmd(String command, String scriptFile, String... params) throws IOException {
        PumpStreamHandler streamHandler = new PumpStreamHandler(new CollectingLogOutputStream());
        // command
        return execCmd(command, scriptFile, params, streamHandler);
    }

    public static int execCmd(String command, String scriptFile, String[] params, PumpStreamHandler streamHandler) throws IOException {
        CommandLine commandline = new CommandLine(command);
        if(!StringUtils.isEmpty(scriptFile)){
            commandline.addArgument(scriptFile);
        }
        if (params != null && params.length > 0) {
            commandline.addArguments(params);
        }
        // execCmd
//        commandline.addArguments("-version");
        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValues(null);
        exec.setStreamHandler(streamHandler);
        int exitValue = exec.execute(commandline);// exit code: 0=success, 1=error
        return exitValue;
    }

    /**
     * 梭哈式非阻塞写法
     * @param commands
     * @param scriptFile
     * @param streamHandler
     * @return
     * @throws IOException
     */
    public static void execCmd(String commands, String scriptFile, PumpStreamHandler streamHandler) throws IOException, InterruptedException {
        CommandLine commandline = CommandLine.parse(commands);
        // execCmd
        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValues(null);
        exec.setStreamHandler(streamHandler);
        //该handler为非阻塞
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        exec.execute(commandline,resultHandler);// exit code: 0=success, 1=error
        resultHandler.waitFor();
//        return exitValue;
    }

    public static String execToString(String command, String scriptFile, String logFile, String... params) throws IOException {
        // 标准输出：print （null if watchdog timeout）
        // 错误输出：logging + 异常 （still exists if watchdog timeout）
        // 标准输入

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, outputStream);
            execCmd(command, scriptFile, params, streamHandler);
        } catch (Exception e) {
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }

            }
        }
        return outputStream.toString("gbk");
    }

    /**
     * 执行系统命令, 返回执行结果
     *
     * @param cmd      需要执行的命令
     * @param distPath 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     */
    public static String execCmd(String cmd, String distPath) {
        File file = new File(distPath);
        int exitValue = 1;
        try {

            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            CommandLine cmdLine = CommandLine.parse("ping www.baidu.com");
            DefaultExecutor executor = new DefaultExecutor();
            executor.setWorkingDirectory(file);
            executor.setExitValues(null);
            ExecuteWatchdog watchdog = new ExecuteWatchdog(600000);
            executor.setWatchdog(watchdog);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            executor.setStreamHandler(streamHandler);
            exitValue = executor.execute(cmdLine);
            //获取程序外部程序执行结果
            String out = outputStream.toString("utf8");
            String error = errorStream.toString("utf8");
            System.out.println(out);
//            log.info("执行命令处理结果exitValue:[{}],out:[{}],error:[{}]",exitValue,out,error);
        } catch (Exception e) {
//            log.error("执行批处理命令异常[{}]", e);
            throw new RuntimeException("RetCode.CMD_ERROR");
        }
//        log.info("结束执行命令和目录:[{}],[{}][{}s]" , cmd,distPath);
        if(exitValue != 0){
            throw new RuntimeException("执行批处理命令失败");
        }
        return String.valueOf(exitValue);
    }



}
