package com.ramostear.unaboot.domain.entity;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@Entity
@ToString
@Table(name="posts")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Post extends UnaEntity implements Serializable {

    private static final long serialVersionUID = 3207127957984356732L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="slug",columnDefinition = "varchar(128) not null")
    private String slug;

    @Column(name = "title",columnDefinition = "varchar(128) not null")
    private String title;

    @Column(name = "status",columnDefinition = "int default 0")
    private Integer status;

    @Column(name = "summary",columnDefinition = "varchar(512) default ''")
    private String summary;

    @Column(name = "thumb",columnDefinition = "varchar(1024) default ''")
    private String thumb;

    @Column(name="visits",columnDefinition = "int default 0")
    private Long visits;

    @Column(name="likes",columnDefinition = "int default 0")
    private Long likes;

    @Column(name = "template",columnDefinition = "varchar(64) not null")
    private String template;

    @Column(name = "allow_chat",columnDefinition = "int default 0")
    private Boolean allowChat;

    @Column(name = "allow_top",columnDefinition = "int default 0")
    private Boolean allowTop;

    @Column(name = "allow_favor",columnDefinition = "int default 0")
    private Boolean allowFavor;

    @Column(name = "author",columnDefinition = "varchar(64) default ''")
    private String author;

    @Column(name = "url",columnDefinition = "varchar(255) default ''")
    private String url;

    @Column(name = "original",columnDefinition = "int default 1")
    private Boolean original;

    @Column(name="keywords",columnDefinition = "varchar(64) default ''")
    private String keywords;

    @Column(name = "description",columnDefinition = "varchar(128) default ''")
    private String description;

    @Column(name = "markdown",columnDefinition = "longtext")
    private String markdown;

    @Column(name = "html",columnDefinition = "longtext")
    private String html;

    @Column(name = "project_url",columnDefinition = "varchar(512) default ''")
    private String projectUrl;

    @Override
    protected void prePersist() {
        super.prePersist();

        id = null;
        if(status == null){
            status = 0;
        }
        if(StringUtils.isBlank(summary)){
            summary = "";
        }
        if(StringUtils.isBlank(thumb)){
            thumb = "";
        }
        if(StringUtils.isBlank(template)){
            template = "";
        }
        if(allowTop == null){
            allowTop = false;
        }

        if(allowChat == null){
            allowChat = false;
        }
        if(visits == null || visits < 0){
            visits = 0L;
        }
        if(likes == null || likes < 0){
            likes = 0L;
        }
        if(allowFavor == null){
            allowFavor = false;
        }
    }
}
