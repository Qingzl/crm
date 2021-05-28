package com.javalearning.workbench.activity.web.controller;

import com.javalearning.settings.domain.User;
import com.javalearning.settings.service.UserService;
import com.javalearning.workbench.activity.domain.Activity;
import com.javalearning.workbench.activity.domain.ActivityRemark;
import com.javalearning.workbench.activity.service.ActivityRemarkService;
import com.javalearning.workbench.activity.service.ActivityService;
import com.javalearning.workbench.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping("/activity")
@Controller
public class ActivityController {

    private ActivityService activityService;
    private UserService userService;
    private ActivityRemarkService activityRemarkService;

    @Autowired
    public ActivityController(ActivityService activityService,UserService userService,ActivityRemarkService activityRemarkService) {
        this.activityService = activityService;
        this.userService = userService;
        this.activityRemarkService = activityRemarkService;
    }

    @RequestMapping("/users")
    @ResponseBody
    public List<User> getUsers(){
        return userService.getAll();
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String,String> add(Activity activity){
        return  activityService.add(activity);
    }

    @RequestMapping("/pageQuery")
    @ResponseBody
    public Page<Activity> pageQuery(String s_name,String s_owner,String s_startDate,String s_endDate,String pageno,String pagesize){
        return activityService.pageQuery(s_name,s_owner,s_startDate,s_endDate,pageno,pagesize);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,String> delete(String[] id){
        return activityService.delete(id);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String,Object> edit(String id){
        return activityService.getById(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update(Activity activity){
        return activityService.update(activity);
    }

    @RequestMapping("/detail")
    public ModelAndView goToDetail(String aid,String owner){
        ModelAndView mv = new ModelAndView();
        mv.addObject("activity",activityService.goToDetail(aid,owner));
        mv.setViewName("forward:/workbench/activity/detail.jsp");
        return mv;
    }

    @RequestMapping("/activityRemark/pageQuery")
    @ResponseBody
    public Page<ActivityRemark> pageQueryForActivityRemark(String pagesize,String pageno,String aid){
        return activityRemarkService.pagequery(pagesize,pageno,aid);
    }

    @RequestMapping("/activityRemark/add")
    @ResponseBody
    public Boolean addForActivityRemark(ActivityRemark activityRemark){
         return activityRemarkService.add(activityRemark);
    }

    @RequestMapping("/activityRemark/remove")
    @ResponseBody
    public Boolean deleteActivityRemark(String rid){
        return activityRemarkService.deleteSingleById(rid);
    }

    @RequestMapping("/activityRemark/update")
    @ResponseBody
    public boolean updateActivityRemark(ActivityRemark a){
        return activityRemarkService.update(a);
    }
}
