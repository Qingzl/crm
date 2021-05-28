package com.javalearning.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeUtil {

    public String getCurrentTime(String pattern){
      return new SimpleDateFormat(pattern).format(new Date());
    }
}
