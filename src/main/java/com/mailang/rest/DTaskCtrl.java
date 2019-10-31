package com.mailang.rest;
import com.mailang.bean.qmodel.DTaskModel;
import com.mailang.bean.qmodel.PunchModel;
import com.mailang.bean.qmodel.RetMessage;
import com.mailang.cons.ERRCode;
import com.mailang.ddtask.DTaskMgr;
import com.mailang.log.XSLogger;
import com.mailang.utils.Utils;
import com.mailang.xsexception.XSException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "rest/dtask")
public class DTaskCtrl
{
    private XSLogger LOG = XSLogger.getLogger(DTaskCtrl.class);

    private DTaskMgr dTaskMgr = new DTaskMgr();

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public RetMessage query(@RequestParam("userId") String userId)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(dTaskMgr.getTaskList(userId));
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/punch", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public RetMessage punch(@RequestBody PunchModel punchModel)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            dTaskMgr.punch(punchModel);
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/redo", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public RetMessage redo(@RequestParam("taskType") String taskType, @RequestParam("id") String id)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(dTaskMgr.getTaskList(taskType));
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancel", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public RetMessage cancel(@RequestParam("taskType") String taskType, @RequestParam("id") String id)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
            retMessage.setCode(ERRCode.SUCCESS);
            retMessage.setData(dTaskMgr.getTaskList(taskType));
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/taskmgr/save", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public RetMessage addTask(@RequestBody Map<String, Object> taskData)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/taskmgr/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public RetMessage updateTask(@RequestBody Map<String, Object> taskData)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/taskmgr/delete", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public RetMessage deleteTask(@RequestParam("id") String id)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/tagsmgr/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public RetMessage addTags(@RequestBody Map<String, Object> tagsData)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/tagsmgr/delete", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public RetMessage deleteTags(@RequestParam("id") String id)
    {
        RetMessage retMessage = new RetMessage();
        try
        {
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
            LOG.error("Error. Msg: {}.", Utils.getStackTrace(e1));
            retMessage.setCode(ERRCode.FAILED);
            retMessage.setMsg(Utils.getStackTrace(e1));
            return retMessage;
        }
    }
}
