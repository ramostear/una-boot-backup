package com.ramostear.unaboot.freemarker.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ramostear.unaboot.common.util.HttpClientUtils;
import com.ramostear.unaboot.domain.dto.GitalkDTO;
import com.ramostear.unaboot.domain.vo.TalkVO;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.SettingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName NewestTalk
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/22 0022 2:03
 * @Version 1.0
 **/
@Service
public class NewestTalk extends TemplateDirective {

    private static final String URL = "http://api.github.com/repos";


    @Autowired
    private SettingService settingService;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        GitalkDTO gitalk = settingService.getGitalk();
        int size = handler.getInteger("size",10);
        if(gitalk.getEnable() == 0){
            handler.put(RESULTS,null).render();
        }else{
            String api_url = URL+"/"+gitalk.getAdmin()+"/"+gitalk.getRepo()+"/issues/comments?sort=created&direction=desc";
            String body = new HttpClientUtils().get(api_url);
            if(StringUtils.isNotBlank(body)){
                JSONArray jsonArray = JSONArray.parseArray(body);
                List<TalkVO> talkVOList = new ArrayList<>(10);
                int flag = 1;
                for(int i=0;i< jsonArray.size();i++){
                    if(flag >=size){
                        break;
                    }
                    JSONObject object = jsonArray.getJSONObject(i);
                    String text = object.getString("body");
                    Date creatAt = object.getDate("created_at");
                    String login = object.getObject("user",JSONObject.class).getString("login");
                    String avatarUrl = object.getObject("user",JSONObject.class).getString("avatar_url");
                    String htmlUrl = object.getObject("user",JSONObject.class).getString("html_url");
                    if(!login.equalsIgnoreCase(gitalk.getAdmin())){
                        TalkVO talk = new TalkVO();
                        talk.setBody(text);
                        talk.setCreateAt(creatAt);
                        talk.setLogin(login);
                        talk.setAvatarUrl(avatarUrl);
                        talk.setHtmlUrl(htmlUrl);
                        talkVOList.add(talk);
                        flag++;
                    }
                }
                handler.put(RESULTS,talkVOList).render();
            }else{
                handler.put(RESULTS,null).render();
            }
        }
    }
}
