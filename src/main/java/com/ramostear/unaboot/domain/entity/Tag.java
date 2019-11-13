package com.ramostear.unaboot.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@Entity
@Table(name = "tags")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Tag extends UnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",columnDefinition = "varchar(64) not null")
    private String name;

    @Column(name = "slug",columnDefinition = "varchar(64) not null")
    private String slug;


    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
    }
}
