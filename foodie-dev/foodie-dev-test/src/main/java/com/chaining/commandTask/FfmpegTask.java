package com.chaining.commandTask;

import com.chaining.utils.CollectingLogOutputStream;
import com.chaining.utils.ScriptUtil;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * @ClassName FfmpegTask
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/28 0028
 * @Version V1.0
 **/
@Component
public class FfmpegTask {
    @Async
    public Future<String> asyncFfmpeg(){
        AsyncResult<String> message = null;
        try {
            ScriptUtil.execCmd("ping www.baidu.com", "",new PumpStreamHandler(new CollectingLogOutputStream()));
            message = new AsyncResult<>("success");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            message = new AsyncResult<>("error"+e.getMessage());
        }
        return message;
    }
}
