package com.wsf.querydsl.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wsf.querydsl.dao.UserDao;
import com.wsf.querydsl.entity.QUser;
import com.wsf.querydsl.entity.QUserRole;
import com.wsf.querydsl.entity.User;
import com.wsf.querydsl.entity.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JPAQueryFactory factory;

    @Transactional(rollbackFor = Exception.class)
    public List<User> test(PageRequest pageRequest) {
        QUser qUser = QUser.user;
        QUserRole qUserRole = QUserRole.userRole;

        //单表分页查询
        QueryResults<User> uqr = this.factory
                .selectFrom(qUser)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetchResults();
        List<User> userList = uqr.getResults();
        Page<User> page1 = new PageImpl<>(userList, pageRequest, uqr.getTotal());
        System.out.println(page1.getTotalElements());
        System.out.println(page1.getTotalPages());

        //单表查询指定字段并返回自定义对象
//        List<User> userList = this.factory
//                .select(Projections.bean(User.class, qUser.id, qUser.username, qUser.pwd))
//                .from(qUser)
//                .fetch();

        //关联查询指定字段并返回自定义对象，动态条件拼接
        String username = "王";
        String sex = "女";
        Predicate predicate = qUser.isNotNull().or(qUser.isNull()); //初始化组装条件(类似where 1=1)
        predicate = StringUtils.isEmpty(username) ? predicate : ExpressionUtils.and(predicate, qUser.username.like("%" + username + "%"));
        predicate = StringUtils.isEmpty(sex) ? predicate : ExpressionUtils.and(predicate, qUser.sex.eq(sex));
//        List<User> userList = this.factory
//                .select(Projections.bean(User.class, qUser.id, qUser.username, qUser.sex, qUserRole.roleId))
//                .from(qUser)
//                .leftJoin(qUserRole)
//                .on(qUser.id.eq(qUserRole.userId))
//                .where(predicate)
//                .fetch();

        //与spring data jpa结合使用，Repository层需继承QuerydslPredicateExecutor接口
//        List<User> userList = (List<User>) this.userDao.findAll(
//                qUser.username.isNotNull()
//                        .and(qUser.username.like("%王%").or(qUser.username.like("%张%"))), qUser.id.desc());
//        Page<User> userPage = this.userDao.findAll(predicate, PageRequest.of(page - 1, pageSize));
//        List<User> userList =  userPage.getContent();
        for (User user : userList) {
            System.out.println(user.getUsername() + "---" + user.getSex() + "---" + user.getUserRoleList().size());
            for (UserRole userRole : user.getUserRoleList()) {
                System.out.println(userRole.getId());
            }
        }

        this.factory.update(qUser).set(qUser.version, 2).where(qUser.sex.eq("男")).execute();


        return userList;
    }
}
