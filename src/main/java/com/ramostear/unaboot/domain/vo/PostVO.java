package com.ramostear.unaboot.domain.vo;

import com.ramostear.unaboot.domain.dto.PostDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostVO extends PostDTO {

    private Set<Integer> tagIds;

    private Integer categoryId;


    public String convertTagIds(){
        if(CollectionUtils.isEmpty(tagIds)){
            return "";
        }else{
            String val = "";
            Iterator<Integer> iterator = tagIds.iterator();
            while (iterator.hasNext()){
                val += iterator.next();
            }
            return val.substring(0,val.length()-1);
        }
    }
}
