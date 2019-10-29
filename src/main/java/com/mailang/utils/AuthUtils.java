package com.mailang.utils;

import com.mailang.jdbc.dao.UserDao;
import com.mailang.jdbc.entity.UserEntity;

public class AuthUtils
{
    private static UserDao userDao = SpringUtils.getBean(UserDao.class);

    public static UserEntity validUser(String userName, String password)
    {
        return userDao.validUser(userName, password);
    }
}
