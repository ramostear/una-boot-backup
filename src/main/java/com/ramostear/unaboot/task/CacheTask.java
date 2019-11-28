package com.ramostear.unaboot.task;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.service.PostService;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramostear on 2019/11/25 0025.
 */
@Slf4j
@Service("cacheTask")
public class CacheTask {

    private static CacheManager cacheManager = CacheManager.newInstance();

    @Autowired
    private PostService postService;

    /**
     * 每天0:00开始清理系统中的缓存数据
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearAllCache(){
        String[] cacheNames = cacheManager.getCacheNames();
        if(cacheNames != null){
            for(String name:cacheNames){
                Ehcache ehcache = cacheManager.getEhcache(name);
                if(name.equals("dayHits")){
                    flushDayHitsToDb(ehcache);
                }
                ehcache.removeAll();
                log.info("{} cache data has been clear.",name);
            }
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
