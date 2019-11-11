package com.ramostear.unaboot.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @ClassName Theme
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/12 0012 5:11
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "themes")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Theme extends UnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",columnDefinition = "varchar(64) not null")
    private String name;

    @Column(name = "thumb",columnDefinition = "varchar(1024) not null")
    private String thumb;

    private Integer status;

    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
        if(status == null) status = 0;
    }
}
