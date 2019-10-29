package com.mailang.listener;

import com.mailang.utils.PROP;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 不能调用使用SpringUtils的初始化方法，listener的加载优先级较高
 * 
 * @Description: [一句话描述该类的功能]
 * @Author:      [c00241496]
 * @Date:        [2017/9/19]
 * @Version:     [iManagerU2000V200R018C10]  
 */
public class XSBookInit implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        PROP.init();

        //DBInit.getInstance().initDB();
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {

    }
}
