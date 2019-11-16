package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.entity.Link;
import com.ramostear.unaboot.domain.vo.LinkVO;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.lang.NonNull;

public interface LinkService extends IUnaService<Link,Integer> {

    @NonNull
    LinkVO convertToLinkVO(@NonNull Link link);
}
