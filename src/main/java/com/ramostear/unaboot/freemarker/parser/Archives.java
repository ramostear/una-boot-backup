package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.vo.ArchiveVO;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.ArchiveService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName Archives
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 21:14
 * @Version 1.0
 **/
@Slf4j
@Service
public class Archives extends TemplateDirective {

    @Autowired
    private ArchiveService archiveService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        List<ArchiveVO> archiveVOList = archiveService.archives();
        handler.put(RESULTS,archiveVOList).render();
    }
}
