package com.javalearning.exceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Map<String,String> loginExceptionHandle(Exception e){
        Map<String,String> map = new HashMap<>();
        map.put("msg",e.getMessage());
        return map;
    }
}
