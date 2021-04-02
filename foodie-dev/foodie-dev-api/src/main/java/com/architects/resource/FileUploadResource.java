package com.architects.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName FileUploadResource
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2021/4/2 0002
 * @Version V1.0
 **/
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload.yml")
@Data
public class FileUploadResource {
    private String imageUserFaceLocation;

    private String imageServerUrl;
}
