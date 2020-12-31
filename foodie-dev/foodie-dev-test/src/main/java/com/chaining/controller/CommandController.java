package com.chaining.controller;

import com.chaining.commandTask.FfmpegTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CommandController
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/12/28 0028
 * @Version V1.0
 **/
@RestController
@RequestMapping("command")
public class CommandController {

    @Autowired
    private FfmpegTask ffmpegTask;

    @GetMapping()
    public ResponseEntity command(){
        ffmpegTask.asyncFfmpeg();
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
