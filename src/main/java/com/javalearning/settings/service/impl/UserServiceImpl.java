package com.javalearning.settings.service.impl;

import com.javalearning.settings.dao.UserDao;
import com.javalearning.settings.domain.User;
import com.javalearning.settings.service.UserService;
import com.javalearning.utils.MD5Util;
import com.javalearning.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private TimeUtil timeUtil;
    private MD5Util md5Util;

    @Autowired
    public UserServiceImpl(UserDao userDao, TimeUtil timeUtil, MD5Util md5Util) {
        this.userDao = userDao;
        this.timeUtil = timeUtil;
        this.md5Util = md5Util;
    }

    @Override
    public Map<String,String> queryByActAndPwd(String loginAct, String loginPwd, HttpServletRequest request) throws LoginException {
        String loginPwdMD5 = md5Util.getMD5(loginPwd);
        User user = userDao.queryByActAndPwd(loginAct,loginPwdMD5);
        if (user == null){
            throw new LoginException("账号密码错误");
        }
        if (timeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss").compareTo(user.getExpireTime()) >= 0){
            throw new LoginException("账号已失效");
        }
        if ("0".equals(user.getLockState())){
            throw new LoginException("账号已被锁定");
        }
        String ip = request.getRemoteAddr();
        String allowIps = user.getAllowIps();
        System.out.println("ip=================="+ip);
        if (!("".equals(allowIps)) && allowIps != null && !allowIps.contains(ip)){
            throw new LoginException("IP限制");
        }
        request.getSession().setAttribute("user",user);
        Map<String,String> map = new HashMap<>();
        map.put("msg","");
        return map;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
}
