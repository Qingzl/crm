package com.javalearning.settings.service;

import com.javalearning.settings.domain.User;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,String> queryByActAndPwd(String loginAct, String loginPwd, HttpServletRequest request) throws LoginException;
    List<User> getAll();
}
