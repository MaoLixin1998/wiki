package com.lixin.wiki.controller;

import com.lixin.wiki.domain.Demo;
import com.lixin.wiki.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mao
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @Resource
    private DemoService demoService;

    @GetMapping("/list")
    public List<Demo> list(){
       return demoService.list();
    }
}
