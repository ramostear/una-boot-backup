package com.ramostear.unaboot.domain.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @ ClassName Category
 * @ Description 栏目，频道，分类，导航实体
 * @ Author ramostear
 * @ Date 2019/11/12 0012 19:20
 * @ Version 1.0
 **/
@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@EqualsAndHashCode(callSuper = true)
public class Category extends UnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",columnDefinition = "varchar(64) not null")
    private String name;

    @Column(name = "slug",columnDefinition = "varchar(64) not null")
    private String slug;

    @Column(name = "parent_id",columnDefinition = "int default 0")
    private Integer parentId;

    @Column(name = "sort_num",columnDefinition = "int default 0")
    private Integer sortNum;

    @Column(name = "keywords",columnDefinition = "varchar(64) default ''")
    private String keywords;

    @Column(name = "description",columnDefinition = "varchar(128) default ''")
    private String description;

    @Column(name = "template",columnDefinition = "varchar(64) not null")
    private String template;

    @Column(name = "allow_nav",columnDefinition = "int default 0")
    private Integer allowNav;

    @Column(name = "project_url",columnDefinition = "varchar(255) default ''")
    private String projectUrl;

    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
        if(parentId == null || parentId < 0){
            parentId = 0;
        }
    }
}
