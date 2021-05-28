package com.javalearning.workbench.activity.service.impl;

import com.github.pagehelper.PageHelper;
import com.javalearning.utils.TimeUtil;
import com.javalearning.utils.UUIDUtil;
import com.javalearning.workbench.activity.dao.ActivityRemarkDao;
import com.javalearning.workbench.activity.domain.ActivityRemark;
import com.javalearning.workbench.activity.service.ActivityRemarkService;
import com.javalearning.workbench.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;

@Service("arsi")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    private ActivityRemarkDao activityRemarkDao;
    private Page<ActivityRemark> page;
    private UUIDUtil uuidUtil;
    private TimeUtil timeUtil;

    @Autowired
    public ActivityRemarkServiceImpl(ActivityRemarkDao activityRemarkDao, Page<ActivityRemark> page, UUIDUtil uuidUtil, TimeUtil timeUtil) {
        this.activityRemarkDao = activityRemarkDao;
        this.page = page;
        this.uuidUtil = uuidUtil;
        this.timeUtil = timeUtil;
    }

    @Override
    public List<ActivityRemark> getByActivityId(String aid) {
        return activityRemarkDao.getByActivityId(aid);
    }

    @Override
    public Page<ActivityRemark> pagequery(String pagesize, String pageno, String aid) {
        page.setPageno(pageno);
        page.setPagesize(pagesize);
        List<ActivityRemark> activityRemarks = activityRemarkDao.getByActivityId(aid);
        int totalsize = activityRemarks.size();
        page.setTotalsize(String.valueOf(totalsize));
        int pagesize1 = Integer.valueOf(pagesize);
        page.setTotalpage(String.valueOf(totalsize%pagesize1==0?totalsize/pagesize1:totalsize/pagesize1+1));

        PageHelper.startPage(Integer.valueOf(pageno),pagesize1);
        List<ActivityRemark> activityRemarkList = activityRemarkDao.getByActivityId(aid);
        page.setDataList(activityRemarkList);
        return page;
    }

    @Override
    public Boolean add(ActivityRemark activityRemark) {
        Boolean success = false;
        activityRemark.setId(uuidUtil.getUUID());
        activityRemark.setCreateTime(timeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        activityRemark.setEditFlag("0");
        if (activityRemarkDao.add(activityRemark) == 1){
            success = true;
        }
        return success;
    }

    @Override
    public Boolean deleteSingleById(String rid) {
        Boolean success = false;
        if (activityRemarkDao.deleteSingleById(rid) == 1){
            success = true;
        }
        return success;
    }

    @Override
    public boolean update(ActivityRemark a) {
        boolean success = false;
        a.setEditTime(timeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        if (activityRemarkDao.update(a) == 1){
            success = true;
        }
        return success;
    }
}
