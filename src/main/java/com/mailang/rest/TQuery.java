package com.mailang.rest;

import com.mailang.bean.qmodel.RTemplateModel;
import com.mailang.bean.qmodel.RetMessage;
import com.mailang.cons.ERRCode;
import com.mailang.cons.XSCons;
import com.mailang.tquery.TemplateDataProvider;
import com.mailang.utils.Utils;
import com.mailang.xsexception.XSException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板查询接口
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月11日
 */
@RestController
public class TQuery
{
    @ResponseBody
    @RequestMapping(value = "rest/template/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public RetMessage getData(@RequestBody RTemplateModel dataModel)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            Map<String, Object> retData = new HashMap<String, Object>();

            Map<String, Object> paramMap = dataModel.getConMap() == null ? new HashMap<String, Object>() : dataModel.getConMap();
            paramMap.put(XSCons.USER_ID, dataModel.getUserId());
            for (String module : dataModel.getQueryId().split(XSCons.SEMICOLON))
            {
                Object data = TemplateDataProvider.getResult(module, paramMap);
                retData.put(module, data);
            }

            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(retData);
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
}
