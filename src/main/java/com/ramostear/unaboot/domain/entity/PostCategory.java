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
@Table(name = "post_category")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostCategory extends UnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name="post_id")
    private Integer postId;


    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
    }
}
