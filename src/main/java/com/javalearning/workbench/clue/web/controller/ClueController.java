package com.javalearning.workbench.clue.web.controller;

import com.javalearning.settings.domain.User;
import com.javalearning.settings.service.UserService;
import com.javalearning.workbench.activity.domain.Activity;
import com.javalearning.workbench.activity.service.ActivityService;
import com.javalearning.workbench.clue.domain.Clue;
import com.javalearning.workbench.clue.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/clue")
public class ClueController {
    private ClueService clueService;
    private UserService userService;
    private ActivityService activityService;

    @Autowired
    public ClueController(ClueService clueService, UserService userService,ActivityService activityService) {
        this.clueService = clueService;
        this.userService = userService;
        this.activityService = activityService;
    }

    @ResponseBody
    @RequestMapping("/users")
    public List<User> getUserList(){
        return userService.getAll();
    }

    @ResponseBody
    @RequestMapping("/save")
    public Boolean save(Clue c){
        return clueService.save(c);
    }

    @RequestMapping("/detail")
    public ModelAndView goToDetail(String cid){
        ModelAndView mv = new ModelAndView();
        mv.addObject("clue",clueService.queryById(cid));
        mv.setViewName("forward:/workbench/clue/detail.jsp");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/activities")
    public List<Activity> getActivitiesByCid(String cid){
        return activityService.getActivitiesByClueId(cid);
    }

    @ResponseBody
    @RequestMapping("/NotBindActivities")
    public List<Activity> getNotBindActivities(String[] id,String name){
        return activityService.getNotBindActivities(id,name);
    }
}
