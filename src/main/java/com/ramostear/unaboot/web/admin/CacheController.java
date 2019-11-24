package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.domain.vo.CacheVO;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
public class CacheController {

    private static CacheManager cacheManager = CacheManager.newInstance();

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

}
