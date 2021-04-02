package com.architects.controller;

import com.architects.bo.CenterUserBO;
import com.architects.pojo.Users;
import com.architects.resource.FileUploadResource;
import com.architects.service.CenterUserService;
import com.architects.utils.CookieUtil;
import com.architects.utils.DateUtil;
import com.architects.utils.JSONUtil;
import com.architects.utils.JSONVO;
import com.architects.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CenterUserController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/4/1 0001
 * @Version V1.0
 **/
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RequestMapping("/userInfo")
@RestController
public class CenterUserController extends BaseController {
    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUploadResource fileUploadResource;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public JSONVO update(@ApiParam(name = "userId", value = "用户id", required = true)
                         @RequestParam String userId,
                         @RequestBody @Valid CenterUserBO centerUserBO,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         BindingResult bindingResult) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (bindingResult.hasErrors()) {
            return JSONVO.errorMap(getErrorMap(bindingResult));
        }

        Users user = centerUserService.updateUserInfo(userId, centerUserBO);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        CookieUtil.setCookie(request, response, "user", JSONUtil.obj2String(userVO), true);

        // todo 后续要改，增加令牌token，会整合进redis，分布式会话

        return JSONVO.ok();
    }

    @ApiOperation(value = "用户头像修改", httpMethod = "POST")
    @PostMapping("/updateFace")
    public JSONVO updateFace(@ApiParam(name = "userId", value = "用户id", required = true)
                             @RequestParam String userId,
                             @ApiParam(name = "file", value = "用户头像", required = true)
                                     MultipartFile file,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        // 在路径上为每一个用户增加一个userId，用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;


        if (file == null) {
            return JSONVO.errorMsg("文件不能为空！");
        }

        String originalFilename = file.getOriginalFilename();
        // 文件重命名 mooc-face.png -> ["mooc-face", "png"]
        String[] fileNameArr = originalFilename.split("\\.");

        // 获取文件的后缀名
        String suffix = fileNameArr[fileNameArr.length - 1];
        if (!(suffix.equalsIgnoreCase("png")
                || suffix.equalsIgnoreCase("jpg")
                || suffix.equalsIgnoreCase("jpeg"))) {
            return JSONVO.errorMsg("图片格式不正确！");
        }

        // face-{userid}.png
        // 文件名称重组，覆盖式上传,增量式：额外拼接当前时间
        String newFileName = "face-" + userId + "." + suffix;

        // 上传的头像最终保存的位置
        String finalFacePath = fileUploadResource.getImageUserFaceLocation() + uploadPathPrefix + File.separator + newFileName;
        //String finalFacePath = IMAGE_USER_FACE_LOCATION + uploadPathPrefix + File.separator + newFileName;
        // 用于提供给web服务访问的地址
        uploadPathPrefix += ("/" + newFileName);

        File outFile = new File(finalFacePath);
        if (outFile.getParentFile() != null) {
            // 创建文件夹
            outFile.getParentFile().mkdirs();
        }

        // 文件输出保存到目录
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile)) {
            IOUtils.copy(file.getInputStream(), fileOutputStream);
        } catch (IOException e) {
        //  log.error("文件保存失败！", e);
        }

        // 获取图片服务地址
        String imageServerUrl = fileUploadResource.getImageServerUrl();
        // 由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可以及时显示
        String finalUserFaceUrl = imageServerUrl
                + uploadPathPrefix
                + "?t="
                + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        // 更新用户头像到数据库
        Users user = centerUserService.updateUserFace(userId, finalUserFaceUrl);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        CookieUtil.setCookie(request, response, "user", JSONUtil.obj2String(userVO), true);

        // todo 后续要改，增加令牌token，会整合进redis，分布式会话
        return JSONVO.ok();
    }


    private Map<String, String> getErrorMap(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            // 发生验证错误所对应的某一个属性
            String field = fieldError.getField();
            // 验证错误的信息
            String defaultMessage = fieldError.getDefaultMessage();

            map.put(field, defaultMessage);
        }

        return map;
    }

}
