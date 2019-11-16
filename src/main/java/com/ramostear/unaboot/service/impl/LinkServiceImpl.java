package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.Link;
import com.ramostear.unaboot.domain.vo.LinkVO;
import com.ramostear.unaboot.repository.LinkRepository;
import com.ramostear.unaboot.service.LinkService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName LinkServiceImpl
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 16:44
 * @Version 1.0
 **/
@Slf4j
@Service("linkService")
public class LinkServiceImpl extends UnaService<Link,Integer> implements LinkService {

    private final LinkRepository linkRepository;

    public LinkServiceImpl(LinkRepository linkRepository) {
        super(linkRepository);
        this.linkRepository = linkRepository;
    }

    @Override
    public LinkVO convertToLinkVO(Link link) {
        LinkVO linkVO = new LinkVO().convertFrom(link);
        return linkVO;
    }
}
