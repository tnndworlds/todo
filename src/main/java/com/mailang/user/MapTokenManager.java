package com.mailang.user;

import com.mailang.bean.qmodel.TokenModel;
import com.mailang.cons.XSCons;
import com.mailang.jdbc.persist.AutoIncreaseSequenceGenerator;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapTokenManager implements TokenManager
{
    public static Map<String, TokenModel> userTokenMap = new HashMap<String, TokenModel>();

    public static MapTokenManager instance;

    private MapTokenManager()
    {

    }

    public static MapTokenManager getInstance()
    {
        if (null == instance)
        {
            instance = new MapTokenManager();
        }
        return instance;
    }

    @Override
    public TokenModel createToken(String userId)
    {
        if (userTokenMap.containsKey(userId))
        {
            userTokenMap.get(userId).setTokenTime(new Date().getTime());
            return userTokenMap.get(userId);
        }
        String tokenId = AutoIncreaseSequenceGenerator.getSerialNum();
        TokenModel tokenModel = new TokenModel(userId, tokenId);
        tokenModel.setTokenTime(new Date().getTime());
        userTokenMap.put(userId, tokenModel);
        return tokenModel;
    }

    @Override
    public boolean checkToken(String userId, String tokenId)
    {
        if (null == userId)
        {
            return false;
        }

        TokenModel userTokenModel = userTokenMap.get(userId);
        if (null == userTokenModel || !userTokenModel.getToken().equals(tokenId) || userTokenModel.isExpire())
        {
            return false;
        }
        //刷新token时间
        userTokenModel.setTokenTime(new Date().getTime());
        return true;
    }

    @Override
    public void deleteToken(String userId)
    {
        userTokenMap.remove(userId);
    }
}
