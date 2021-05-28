package com.javalearning.dic.web.listener;

import com.javalearning.dic.service.DicService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.Set;

public class DicInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        DicService ds = (DicService) applicationContext.getBean("dicServiceImpl");
        Map<String,Object> map = ds.dicInit();
        Set<String> keys = map.keySet();
        ServletContext application = sce.getServletContext();
        for (String key:keys) {
            application.setAttribute(key,map.get(key));
        }
    }
}
