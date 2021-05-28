package com.javalearning.settings.web.controller;

import com.javalearning.settings.domain.User;
import com.javalearning.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,String> login(String loginAct, String loginPwd, HttpServletRequest request) throws LoginException {
        return userService.queryByActAndPwd(loginAct,loginPwd,request);
    }

}
