package com.community.controller;


import com.community.mapper.UserMapper;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class  IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")/*访问首页*/
    public String index(HttpServletRequest request) {
        /*循环看所有的cookies，找到其中为token的cookie，拿到这个cookies到数据库中查是不是有这个cookie，
        如果有，就把对应的user放到session里面，如果没有，返回登录界面*/

        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie: cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user !=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
