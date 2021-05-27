package com.lixin.wiki.service;

import com.lixin.wiki.domain.Ebook;
import com.lixin.wiki.domain.EbookExample;
import com.lixin.wiki.mapper.EbookMapper;
import com.lixin.wiki.req.EbookReq;
import com.lixin.wiki.resp.EbookResp;
import com.lixin.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mao
 */
@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
//            respList.add(ebookResp);
//        对象复制
//        EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//        }

        //列表负责
        respList = CopyUtil.copyList(ebookList, EbookResp.class);
        return respList;
    }

}
