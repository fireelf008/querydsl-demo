package com.wsf.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wsf.querydsl.dao.UserDao;
import com.wsf.querydsl.entity.QUser;
import com.wsf.querydsl.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class QueryDslTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JPAQueryFactory factory;

    @Test
    @Transactional
    @Commit
    public void test() {

        QUser qUser = QUser.user;
        List<User> userList = this.factory.select(qUser).from(qUser).fetch();
        for (User user : userList) {
            System.out.println(user.getUsername());
        }
    }
}
