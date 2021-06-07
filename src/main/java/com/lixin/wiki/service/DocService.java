package com.lixin.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lixin.wiki.domain.Content;
import com.lixin.wiki.domain.Doc;
import com.lixin.wiki.domain.DocExample;
import com.lixin.wiki.mapper.ContentMapper;
import com.lixin.wiki.mapper.DocMapper;
import com.lixin.wiki.req.DocQueryReq;
import com.lixin.wiki.req.DocSaveReq;
import com.lixin.wiki.resp.DocQueryResp;
import com.lixin.wiki.resp.PageResp;
import com.lixin.wiki.util.CopyUtil;
import com.lixin.wiki.util.SnowFlake;
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
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocQueryResp> list(DocQueryReq req) {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());


//        for (Doc doc : docList) {
//            DocResp docResp = new DocResp();
//            BeanUtils.copyProperties(doc,docResp);
//            respList.add(docResp);
//        对象复制
//        DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//        }

        List<DocQueryResp> respList = new ArrayList<>();
        //列表负责
        respList = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    public List<DocQueryResp> all(Long ebookId) {

        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);
        List<DocQueryResp> respList = new ArrayList<>();
        //列表负责
        respList = CopyUtil.copyList(docList, DocQueryResp.class);

        return respList;
    }

    /**
     * 保存
     *
     * @param req
     */
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);

        if (ObjectUtils.isEmpty(req.getId())) {
            //新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            //更新
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        return content.getContent();
    }
}
