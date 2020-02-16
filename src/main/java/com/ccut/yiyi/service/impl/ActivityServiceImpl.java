package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.ActivityDao;
import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: ActivityServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 15:18
 * @version: V1.0
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Override
    public void add(String loginStuCode, Activity activity) {
        //设置添加活动的管理员学号
        activity.setStuCode(loginStuCode);
        //活动默认审核状态为0,0表示未通过，1表示通过
        activity.setMark(0);
        activityDao.save(activity);
    }
}
