package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.vo.CacheVO;
import com.ramostear.unaboot.service.PostService;
import com.ramostear.unaboot.web.UnaController;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CacheController
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/25 0025 1:05
 * @Version 1.0
 **/
@Controller
@RequestMapping("/admin/settings/cache")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class CacheController extends UnaController {

    private static CacheManager cacheManager = CacheManager.newInstance();


    @Autowired
    private PostService postService;

    @GetMapping
    public String index(Model model){
        String[] cacheNames = cacheManager.getCacheNames();
        List<CacheVO> cacheVOList = new ArrayList<>();
        if(cacheNames != null){
            for(String name:cacheNames){
                Ehcache ehcache = cacheManager.getEhcache(name);
                if(ehcache != null){
                    CacheVO cache = new CacheVO();
                    cache.setName(name);
                    cache.setSize(ehcache.getKeys().size());
                    cache.setMemorySize(ehcache.getMemoryStoreSize());
                    cache.setDiskSize(ehcache.getDiskStoreSize());
                    cacheVOList.add(cache);
                }
            }
        }
        model.addAttribute("caches",cacheVOList);
        return "/admin/setting/cache";
    }

    @PostMapping("/clear")
    public ResponseEntity<Object> clearAll(){
        String[] cacheNames = cacheManager.getCacheNames();
        if(cacheNames != null){
            for(String name:cacheNames){
                Ehcache ehcache = cacheManager.getEhcache(name);
                if(name.equals("dayHits")){
                    flushDayHitsToDb(ehcache);
                }
                ehcache.removeAll();
            }
            return ok();
        }else{
            return badRequest();
        }
    }

    @PostMapping("/clear/{name}")
    public ResponseEntity<Object> clearBy(@PathVariable("name")String name){
        Ehcache ehcache = cacheManager.getEhcache(name);
        if(ehcache != null){
            if(name.equals("dayHits")){
                flushDayHitsToDb(ehcache);
            }
            ehcache.removeAll();
            return ok();
        }else{
            return badRequest();
        }
    }

    private void flushDayHitsToDb(Ehcache ehcache){
        List<String> keys = ehcache.getKeys();
        List<Post> temp = new ArrayList<>();
        if(!CollectionUtils.isEmpty(keys)){
            keys.forEach(key->{
                Element element = ehcache.get(key);
                Integer postId = Integer.parseInt(key.split("_")[0]);
                long count =0;
                if(element != null){
                    count = (Long) element.getObjectValue();
                }
                Post post = postService.getById(postId);
                if(post != null){
                    post.setVisits(post.getVisits()+count);
                    temp.add(post);
                }
            });
        }
        if(temp.size() > 0){
            postService.updateInBatch(temp);
        }
    }

}
