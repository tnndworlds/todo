package com.mailang.filter;

import com.mailang.bean.qmodel.Authorization;
import com.mailang.cons.XSCons;
import com.mailang.user.MapTokenManager;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author chengsongsong
 * @Date 2019/9/2
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String tokenId = request.getHeader(XSCons.AUTHORIZATION);
        String userId = request.getHeader(XSCons.USER_ID);
        if(MapTokenManager.getInstance().checkToken(userId, tokenId))
        {
            request.setAttribute(XSCons.USER_ID, userId);
            return true;
        }

        if (method.getAnnotation(Authorization.class) != null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}