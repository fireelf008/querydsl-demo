package com.wsf.querydsl.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wsf.querydsl.utils.IdUtils")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private User user;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "enabled")
    private Integer enabled = 1;

    @Column(name = "version")
    private Integer version;
}
