package com.suny.interceptor;

import com.suny.dao.LoginTicketDAO;
import com.suny.dao.UserDAO;
import com.suny.model.HostHolder;
import com.suny.model.LoginTicket;
import com.suny.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by 孙建荣 on 17-9-2.下午10:12
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    private LoginTicketDAO loginTicketDAO;

    private UserDAO userDAO;

    private HostHolder hostHolder;

    public PassportInterceptor() {
    }

    public PassportInterceptor(LoginTicketDAO loginTicketDAO, UserDAO userDAO, HostHolder hostHolder) {
        this.loginTicketDAO = loginTicketDAO;
        this.userDAO = userDAO;
        this.hostHolder = hostHolder;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}














