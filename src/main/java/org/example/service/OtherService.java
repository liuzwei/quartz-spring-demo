package org.example.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhaowei
 * @date 2022/7/11 16:41
 */
@Slf4j
@Service
public class OtherService {

    public void doSomething(){
        log.info("this is other service ,do something !");
    }
}
