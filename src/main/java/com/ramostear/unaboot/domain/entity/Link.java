package com.ramostear.unaboot.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Data
@Entity
@Table(name = "links")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Link extends UnaEntity implements Serializable {

    private static final long serialVersionUID = -8089034796881732069L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",columnDefinition = "varchar(50) not null")
    private String name;

    @Column(name="url",columnDefinition = "varchar(256) not null")
    private String url;

    @Column(name = "sort_num",columnDefinition = "int default 0")
    private Integer sortNum;

    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
    }
}
