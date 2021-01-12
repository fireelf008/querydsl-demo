package com.wsf.querydsl.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wsf.querydsl.utils.IdUtils")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "sex")
    private String sex;

    @Column(name = "age")
    private Integer age;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "enabled")
    private Integer enabled = 1;

    @Column(name = "version")
    private Integer version;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<UserRole> userRoleList;
}
