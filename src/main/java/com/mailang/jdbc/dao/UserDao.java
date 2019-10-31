package com.mailang.jdbc.dao;

import com.mailang.jdbc.entity.UserEntity;
import com.mailang.jdbc.persist.DBUtils;
import com.mailang.xsexception.XSException;

import java.util.Map;

public class UserDao extends AbstractDao<UserEntity>
{
    public UserEntity validUser(String userId, String password)
    {
        Map<String, Object> dataMap = this.queryById(userId);
        if (null == dataMap)
        {
            return null;
        }
        UserEntity userEntity = DBUtils.getEntity(UserEntity.class, dataMap);
        if (userEntity.getPassword().equals(password))
        {
            return userEntity;
        }
        return null;
    }

    public void register(UserEntity userEntity)
    {
        if (null != this.queryById(userEntity.getId()))
        {
            throw new XSException(11000001);
        }
        this.save(userEntity);
    }
}
