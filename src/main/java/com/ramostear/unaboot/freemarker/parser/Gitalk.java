package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.dto.GitalkDTO;
import com.ramostear.unaboot.freemarker.parser.abs.BaseMethod;
import com.ramostear.unaboot.service.SettingService;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName Gitalk
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/22 0022 3:16
 * @Version 1.0
 **/
@Service
public class Gitalk extends BaseMethod {

    @Autowired
    private SettingService settingService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        GitalkDTO gitalk = settingService.getGitalk();
        if(gitalk.getEnable() == 0){
            return "";
        }else{
           Integer postId = getInteger(arguments,0);
           String container = getString(arguments,1);
           StringBuilder sb = new StringBuilder();
           sb.append("var gitalk = new Gitalk({");
           sb.append("clientID:'"+gitalk.getClientId()+"',")
             .append("clientSecret:'"+gitalk.getClientSecret()+"',")
             .append("repo:'"+gitalk.getRepo()+"',")
             .append("owner:'"+gitalk.getOwner()+"',")
             .append("admin:'"+gitalk.getAdmin()+"',")
             .append("id:'post_"+postId+"'")
             .append("});");
           sb.append("gitalk.render('"+container+"');");
           return sb.toString();
        }
    }
}
