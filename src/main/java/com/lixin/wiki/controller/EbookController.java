package com.lixin.wiki.controller;

import com.lixin.wiki.domain.Ebook;
import com.lixin.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mao
 */
@RestController
@RequestMapping(value = "/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public List<Ebook> list(){
       return ebookService.list();
    }
}
