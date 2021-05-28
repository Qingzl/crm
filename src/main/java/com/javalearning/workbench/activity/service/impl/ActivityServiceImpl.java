package com.javalearning.workbench.activity.service.impl;

import com.github.pagehelper.PageHelper;
import com.javalearning.settings.dao.UserDao;
import com.javalearning.settings.domain.User;
import com.javalearning.utils.TimeUtil;
import com.javalearning.utils.UUIDUtil;
import com.javalearning.workbench.activity.dao.ActivityDao;
import com.javalearning.workbench.activity.dao.ActivityRemarkDao;
import com.javalearning.workbench.activity.domain.Activity;
import com.javalearning.workbench.activity.service.ActivityService;
import com.javalearning.workbench.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao;
    private UUIDUtil uuidUtil;
    private TimeUtil timeUtil;
    private Page<Activity> page;
    private UserDao userDao;
    private ActivityRemarkDao activityRemarkDao;

    @Autowired
    public ActivityServiceImpl(ActivityDao activityDao, UUIDUtil uuidUtil, TimeUtil timeUtil,Page<Activity> page,UserDao userDao,ActivityRemarkDao activityRemarkDao) {
        this.activityDao = activityDao;
        this.uuidUtil = uuidUtil;
        this.timeUtil = timeUtil;
        this.page = page;
        this.userDao = userDao;
        this.activityRemarkDao = activityRemarkDao;
    }

    @Override
    public Map<String, String> add(Activity activity) {
        Map<String, String> map = new HashMap<>();
        activity.setId(uuidUtil.getUUID());
        activity.setCreateTime(timeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        int count = activityDao.add(activity);
        if(count == 1){
            map.put("success","true");
        }else{
            map.put("success","false");
        }
        return map;
    }

    @Override
    public Page<Activity> pageQuery(String s_name,String s_owner,String s_startDate,String s_endDate,String pageno,String pagesize) {
        page.setPageno(pageno);
        page.setPagesize(pagesize);
        List<Activity> activityList = activityDao.getByCondition(s_name,s_owner,s_startDate,s_endDate);
        int totalsize = activityList.size();
        page.setTotalsize(String.valueOf(totalsize));
        int pages = Integer.parseInt(pagesize);
        page.setTotalpage(String.valueOf(totalsize%pages==0?totalsize/pages:totalsize/pages+1));
        PageHelper.startPage(Integer.parseInt(pageno),pages);
        List<Activity> activities = activityDao.getByCondition(s_name,s_owner,s_startDate,s_endDate);
        page.setDataList(activities);
        return page;
    }

    @Override
    @Transactional
    public Map<String,String> delete(String[] activityIds) {
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        int count1 = activityRemarkDao.queryById(activityIds);
        int count2 = activityRemarkDao.deleteById(activityIds);
        if (count1 == count2){
            if (activityDao.delete(activityIds) == activityIds.length){
                map.put("success","true");
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> getById(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("activity",activityDao.getById(id));
        map.put("users",userDao.getAll());
        return map;
    }

    @Override
    public Map<String,Object> update(Activity activity) {
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        activity.setEditTime(timeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        if (activityDao.update(activity) == 1){
            map.put("success",true);
        }
        return map;
    }

    @Override
    public Activity goToDetail(String aid,String owner) {
        Activity activity = activityDao.getById(aid);
        activity.setOwner(owner);
        return activity;
    }

    @Override
    public List<Activity> getActivitiesByClueId(String cid) {
        return activityDao.getByClueId(cid);
    }

    @Override
    public List<Activity> getNotBindActivities(String[] id, String name) {
        return activityDao.getNotBind(id,name);
    }
}
