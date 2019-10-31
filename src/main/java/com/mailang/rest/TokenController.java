package com.mailang.rest;

import com.mailang.bean.qmodel.Authorization;
import com.mailang.bean.qmodel.RetMessage;
import com.mailang.bean.qmodel.TokenModel;
import com.mailang.cons.ERRCode;
import com.mailang.jdbc.dao.UserDao;
import com.mailang.jdbc.entity.UserEntity;
import com.mailang.jdbc.persist.DBUtils;
import com.mailang.tquery.TemplateDataProvider;
import com.mailang.user.MapTokenManager;
import com.mailang.utils.AuthUtils;
import com.mailang.utils.SpringUtils;
import com.mailang.xsexception.XSException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "rest/user")
public class TokenController
{
    private static Logger LOG = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private UserDao userDao;

    @ResponseBody
    @RequestMapping(value="/register", method= RequestMethod.POST, produces="application/json;charset=UTF-8")
    public RetMessage register(@RequestBody UserEntity userEntity)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            UserDao userDao = SpringUtils.getBean(UserDao.class);
            userDao.register(userEntity);
            retMessage.setCode(ERRCode.SUCCESS);
            return retMessage;
        }
        catch (XSException e)
        {
            retMessage.setCode(e.getCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(e1.getMessage());
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value="/login", method= RequestMethod.POST, produces="application/json;charset=UTF-8")
    public RetMessage login(@RequestBody UserEntity userEntity)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            if (StringUtils.isBlank(userEntity.getId()) || StringUtils.isBlank(userEntity.getPassword()))
            {
                throw new XSException("User name or password is empty.", ERRCode.FAILED);
            }

            //鉴权操作
            UserEntity validUser = AuthUtils.validUser(userEntity.getId(), userEntity.getPassword());
            if (null == validUser)
            {
                throw new XSException("User name or password is error.", ERRCode.FAILED);
            }
            Map<String, Object> conMap = new HashMap<>();
            conMap.put("userId", userEntity.getId());
            TokenModel tokenModel = MapTokenManager.getInstance().createToken(userEntity.getId());
            tokenModel.setUserEntity(validUser);
            tokenModel.setUserInfo(TemplateDataProvider.getResult("UserInfo", conMap));
            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(tokenModel);
            return retMessage;
        }
        catch (XSException e)
        {
            retMessage.setCode(e.getCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(e1.getMessage());
            return retMessage;
        }
    }

    @ResponseBody
    @Authorization
    @RequestMapping(value = "/currentUser", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
    public RetMessage getUser(@RequestParam("userId") String userId)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            //鉴权操作
            Map<String, Object> dataMap = userDao.queryById(userId);
            if (null == dataMap)
            {
                throw new XSException("User name or password is error.", ERRCode.FAILED);
            }
            Map<String, Object> conMap = new HashMap<>();
            conMap.put("userId", userId);

            JSONObject retData = new JSONObject();
            retData.put("userEntity", DBUtils.getEntity(UserEntity.class, dataMap));
            retData.put("userInfo", TemplateDataProvider.getResult("UserInfo", conMap));
            retData.put("userList", TemplateDataProvider.getResult("AllUserList", conMap));

            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(retData);
            return retMessage;
        }
        catch (XSException e)
        {
            retMessage.setCode(e.getCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(e1.getMessage());
            return retMessage;
        }
    }

    @ResponseBody
    @Authorization
    @RequestMapping(value = "/logout", method= RequestMethod.DELETE, produces="application/json;charset=UTF-8")
    public RetMessage logout(@RequestParam("userName") String userName)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            MapTokenManager.getInstance().deleteToken(userName);
            retMessage.setCode(ERRCode.SUCCESS);
            return retMessage;
        }
        catch (XSException e)
        {
            LOG.error("XSError. Msg: {}.", e.getMessage());
            retMessage.setCode(e.getCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(e1.getMessage());
            return retMessage;
        }
    }
}