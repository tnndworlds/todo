package com.mailang.rest;

import com.mailang.bean.qmodel.Authorization;
import com.mailang.bean.qmodel.RetMessage;
import com.mailang.bean.qmodel.TokenModel;
import com.mailang.cons.ERRCode;
import com.mailang.jdbc.dao.UserDao;
import com.mailang.jdbc.entity.UserEntity;
import com.mailang.jdbc.persist.DBUtils;
import com.mailang.log.XSLogger;
import com.mailang.user.MapTokenManager;
import com.mailang.utils.AuthUtils;
import com.mailang.utils.SpringUtils;
import com.mailang.utils.Utils;
import com.mailang.xsexception.XSException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "rest/user")
public class TokenController
{
    private static XSLogger LOG = XSLogger.getLogger(TokenController.class);

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
            LOG.error("XSError. Msg: {}.", e.getMessage());
            retMessage.setCode(e.getErrCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.UNKNOW_EXCEPTION);
            retMessage.setMsg(Utils.getStackTrace(e1));
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
            if (StringUtils.isBlank(userEntity.getName()) || StringUtils.isBlank(userEntity.getPassword()))
            {
                throw new XSException(ERRCode.USERNAME_PASSOWR_EMPTY);
            }

            //鉴权操作
            UserEntity validUser = AuthUtils.validUser(userEntity.getName(), userEntity.getPassword());
            if (null == validUser)
            {
                throw new XSException(ERRCode.USERNAME_PASSOWR_ERROR);
            }
            TokenModel tokenModel = MapTokenManager.getInstance().createToken(userEntity.getName());
            tokenModel.setUserEntity(validUser);
            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(tokenModel);
            return retMessage;
        }
        catch (XSException e)
        {
            LOG.error("XSError. Msg: {}.", e.getMessage());
            retMessage.setCode(e.getErrCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.UNKNOW_EXCEPTION);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @Authorization
    @RequestMapping(value = "/currentUser", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
    public RetMessage getUser(@RequestParam("userId")String userId)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            //鉴权操作
            Map<String, Object> dataMap = userDao.queryById(userId);
            if (null == dataMap)
            {
                throw new XSException(ERRCode.USERNAME_PASSOWR_ERROR);
            }

            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(DBUtils.getEntity(UserEntity.class, dataMap));
            return retMessage;
        }
        catch (XSException e)
        {
            retMessage.setCode(e.getErrCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            retMessage.setCode(ERRCode.UNKNOW_EXCEPTION);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @Authorization
    @RequestMapping(value = "/logout", method= RequestMethod.DELETE, produces="application/json;charset=UTF-8")
    public RetMessage logout(@RequestParam("userName")String userName)
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
            retMessage.setCode(e.getErrCode());
            retMessage.setMsg(e.getMessage());
            return retMessage;
        }
        catch (Exception e1)
        {
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.UNKNOW_EXCEPTION);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }
}
