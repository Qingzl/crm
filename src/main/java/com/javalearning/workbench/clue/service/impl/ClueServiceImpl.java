package com.javalearning.workbench.clue.service.impl;

import com.javalearning.utils.TimeUtil;
import com.javalearning.utils.UUIDUtil;
import com.javalearning.workbench.clue.dao.ClueDao;
import com.javalearning.workbench.clue.domain.Clue;
import com.javalearning.workbench.clue.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao;
    private UUIDUtil uuidUtil;
    private TimeUtil timeUtil;

    @Autowired
    public ClueServiceImpl(ClueDao clueDao, UUIDUtil uuidUtil, TimeUtil timeUtil) {
        this.clueDao = clueDao;
        this.uuidUtil = uuidUtil;
        this.timeUtil = timeUtil;
    }

    @Override
    public Boolean save(Clue c) {
        boolean success = true;
        c.setId(uuidUtil.getUUID());
        c.setCreateTime(timeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        if (clueDao.save(c) != 1){
            success = false;
        }
        return success;
    }

    @Override
    public Clue queryById(String cid) {
        return clueDao.queryById(cid);
    }
}
