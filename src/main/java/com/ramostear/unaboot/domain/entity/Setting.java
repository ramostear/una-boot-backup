package com.ramostear.unaboot.domain.entity;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @ClassName Setting
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/13 0013 2:57
 * @Version 1.0
 **/
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="settings")
@ToString(callSuper = true)
public class Setting extends UnaEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="_key")
    private String key;

    @Lob
    @Type(type = "text")
    @Column(name = "_value")
    private String value;

    @Override
    protected void prePersist() {
        id = null;
        if(StringUtils.isBlank(key)){
            key = "";
        }
        if(StringUtils.isBlank(value)){
            value = "";
        }
        super.prePersist();
    }
}
