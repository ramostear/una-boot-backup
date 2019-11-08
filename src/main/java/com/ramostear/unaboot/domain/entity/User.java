package com.ramostear.unaboot.domain.entity;

import com.ramostear.unaboot.common.UnaConst;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @ClassName User
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:07
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "user")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends UnaEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "int not null")
    private Integer id;

    @Column(name = "username",columnDefinition = "varchar(64) not null")
    private String username;

    @Column(name = "password",columnDefinition = "varchar(255) not null")
    private String password;

    @Column(name = "role",columnDefinition = "varchar(64) not null")
    private String role;

    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
        role = UnaConst.DEFAULT_ROLE_NAME;
    }
}
