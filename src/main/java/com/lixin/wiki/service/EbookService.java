package com.lixin.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lixin.wiki.domain.Ebook;
import com.lixin.wiki.domain.EbookExample;
import com.lixin.wiki.mapper.EbookMapper;
import com.lixin.wiki.req.EbookReq;
import com.lixin.wiki.resp.EbookResp;
import com.lixin.wiki.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mao
 */
@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%"+req.getName()+"%");
        }
        PageHelper.startPage(1,3);
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info( "总行数：{}",pageInfo.getTotal());
        LOG.info( "总页数：{}",pageInfo.getPages());

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
