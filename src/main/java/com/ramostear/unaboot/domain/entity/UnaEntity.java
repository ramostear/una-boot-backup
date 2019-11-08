package com.ramostear.unaboot.domain.entity;

import com.ramostear.unaboot.common.util.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Entity
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:08
 * @Version 1.0
 **/
@MappedSuperclass
@Data
@ToString
@EqualsAndHashCode
public class UnaEntity {
    @Column(name = "create_time",columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time",columnDefinition = "timestamp")
    private Date updateTime;

    @Column(name = "deleted",columnDefinition = "TINYINT default 0")
    private Boolean deleted = false;

    @PrePersist
    protected void prePersist(){
        deleted = false;
        Date current = DateUtils.now();
        if(createTime == null) createTime = current;
        if(updateTime == null) updateTime = current;
    }

    @PreUpdate
    protected void preUpdate(){
        updateTime = DateUtils.now();
    }

    @PreRemove
    protected void preRemove(){
        updateTime = DateUtils.now();
    }
}
