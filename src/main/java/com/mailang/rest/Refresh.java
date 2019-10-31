package com.mailang.rest;

import com.mailang.bean.qmodel.RetMessage;
import com.mailang.cons.ERRCode;
import com.mailang.tquery.TemplateDataProvider;
import com.mailang.utils.Utils;
import com.mailang.xsexception.XSException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "rest/refresh")
public class Refresh
{
    @ResponseBody
    @RequestMapping(value = "/tquery", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RetMessage query()
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            TemplateDataProvider.initTemplate();
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
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

}
