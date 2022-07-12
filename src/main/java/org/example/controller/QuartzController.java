package org.example.controller;

import org.example.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhaowei
 * @date 2022/7/11 16:37
 */
@Slf4j
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private CustomService customService;

    @GetMapping("/add")
    public String addQuartz(){
        try {
            customService.addJob();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "SUCCESS";
    }
}
