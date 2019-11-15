package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.freemarker.parser.abs.BaseMethod;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Slf4j
@Service
public class NumFormat extends BaseMethod {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        int number = getInteger(arguments,0);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(number);
    }
}
