package com.mailang.bean.qmodel;

import com.mailang.jdbc.entity.UserEntity;
import com.mailang.user.MapTokenManager;

import java.util.Date;

public class TokenModel
{
    private static final Long MAX_EXPIRE_TIME = 6000000L;
    private String userId;
    private String token;
    private Long tokenTime;
    private UserEntity userEntity;
    private Object userData;

    public TokenModel()
    {

    }
    public TokenModel(String userId, String token)
    {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Long getTokenTime()
    {
        return tokenTime;
    }

    public void setTokenTime(Long tokenTime)
    {
        this.tokenTime = tokenTime;
    }

    public UserEntity getUserEntity()
    {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity)
    {
        this.userEntity = userEntity;
    }

    public boolean isExpire()
    {
        Long expireTime = new Date().getTime() - this.tokenTime;
        if (expireTime > MAX_EXPIRE_TIME)
        {
            MapTokenManager.getInstance().deleteToken(this.userId);
            return true;
        }
        return false;
    }

    public static Long getMaxExpireTime()
    {
        return MAX_EXPIRE_TIME;
    }

    public Object getUserData()
    {
        return userData;
    }

    public void setUserData(Object userData)
    {
        this.userData = userData;
    }
}

