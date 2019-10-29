package com.mailang.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CROSSFilter implements Filter
{
    public void doFilter(ServletRequest req, ServletResponse res,
     FilterChain chain) throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse)res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,userId,token");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig)
    {
    }

    public void destroy()
    {
    }

}

