package com.lixin.wiki.controller;

import com.lixin.wiki.req.EbookReq;
import com.lixin.wiki.resp.CommonResp;
import com.lixin.wiki.resp.EbookResp;
import com.lixin.wiki.resp.PageResp;
import com.lixin.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mao
 */
@RestController
@RequestMapping(value = "/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
