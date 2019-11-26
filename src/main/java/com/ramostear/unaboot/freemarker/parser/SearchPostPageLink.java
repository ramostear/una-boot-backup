package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.param.PostQuery;
import com.ramostear.unaboot.freemarker.parser.abs.BaseMethod;
import com.ramostear.unaboot.service.PostCategoryService;
import com.ramostear.unaboot.service.PostService;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CategoryPostPage
 * @Description ${una_categoryPostPage("${una}/${category.slug}?offset=1",${category.id},offset,15,15)}
 * @Author ramostear
 * @Date 2019/11/22 0022 23:40
 * @Version 1.0
 **/
@Service
public class SearchPostPageLink extends BaseMethod {
    @Autowired
    private PostService postService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String url = getString(arguments,0);
        String keyword = getString(arguments,1) == null?"":getString(arguments,1);
        Integer offset = getInteger(arguments,2)==null?1:getInteger(arguments,2);
        Integer spans = getInteger(arguments,3)==null?5:getInteger(arguments,3);
        Integer size = getInteger(arguments,4)==null?15:getInteger(arguments,4);
        if(StringUtils.isBlank(keyword)){
            return "";
        }
        PostQuery postQuery = new PostQuery();
        postQuery.setStatus(1);
        postQuery.setKeyword(keyword);
        Page<Post> data = postService.search(postQuery,PageRequest.of(offset-1,size));

        int span = (spans-3)/2;
        int pageNo = data.getNumber()+1;
        String curl;
        if(url.indexOf("?") != -1){
            curl = url+"&offset=";
        }else{
            curl = url+"?offset=";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<ul class='pagination'>");
        if(pageNo > 1){
            int prev = pageNo-1;
            sb.append("<li class='page-item'>")
              .append("<a class='page-link' href='"+curl+prev+"'>Previous</a>")
              .append("</li>");
        }else{
            sb.append("<li class='page-item disabled'>")
              .append("<a class='page-link' href='javascript:void(0);'>Previous</a>")
              .append("</li>");
        }
        int totalNo = (span*2)+3;
        int totalNo1 = totalNo-1;

        if(data.getTotalPages() > totalNo){
            if(pageNo <= (span+2)){
                for(int i=1;i<=totalNo1;i++){
                    pageLink(sb,pageNo,i,curl);
                }
                pageLink(sb,0,0,"javascript:void(0);");
                pageLink(sb,pageNo,data.getTotalPages(),curl);
            }else if(pageNo>(data.getTotalPages()-(span+2))){
                pageLink(sb,pageNo,1,curl);
                pageLink(sb,0,0,"javascript:void(0);");
                int num = data.getTotalPages() - totalNo + 2;
                for(int i=num;i<=data.getTotalPages();i++){
                    pageLink(sb,pageNo,i,curl);
                }
            }else{
                pageLink(sb,pageNo,1,curl);
                pageLink(sb,0,0,"javascript:void(0);");
                int num = pageNo-span;
                int num2 = pageNo + span;
                for(int i=num;i<=num2;i++){
                    pageLink(sb,pageNo,i,curl);
                }
                pageLink(sb,0,0,"javascript:void(0);");
                pageLink(sb,pageNo,data.getTotalPages(),curl);
            }
        }else if(data.getTotalPages() > 1){
            for(int i=1;i<=data.getTotalPages();i++){
                pageLink(sb,pageNo,i,curl);
            }
        }else{
            pageLink(sb,1,1,curl);
        }

        if(pageNo < data.getTotalPages()){
            int next = pageNo+1;
            sb.append("<li class='page-item'>")
              .append("<a class='page-link' href='"+curl+next+"'>Next</a>")
              .append("</li>");
        }else{
            sb.append("<li class='page-item disabled'>")
              .append("<a class='page-link' href='javascript:void(0);'>Next</a>")
              .append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }


    private void pageLink(StringBuffer sb,int pageNo,int index,String curl){
        if(index == 0){
            sb.append("<li><span style='margin:0 5px;letter-spacing:5px;font-size:16px'>...</span></li>");
        }else if(pageNo == index){
            sb.append("<li class='page-item active'>")
              .append("<a class='page-link' href='javascript:void(0);'>"+index+"<span class='sr-only'>(current)</span></a>")
              .append("</li>");
        }else{
            sb.append("<li class='page-item'>")
              .append("<a class='page-link' href='"+curl+index+"'>"+index+"</a>")
              .append("</li>");
        }
    }
}
