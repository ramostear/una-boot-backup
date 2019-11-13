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
@Table(name = "post_tag")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostTag extends UnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "post_id")
    private Integer postId;


    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
    }
}
