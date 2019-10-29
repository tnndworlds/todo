package com.mailang.user;

import com.mailang.bean.qmodel.TokenModel;

public interface TokenManager
{
    public TokenModel createToken(String userId);

    public boolean checkToken(String userId, String tokenId);

    public void deleteToken(String userId);
}
