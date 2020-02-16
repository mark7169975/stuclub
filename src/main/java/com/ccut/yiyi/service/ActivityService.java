package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Activity;

/**
 * @ClassName: ActivityService  活动业务层接口
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 15:15
 * @version: V1.0
 */
public interface ActivityService {
    /**
     * 添加社团活动
     * @param loginStuCode  添加活动的管理员学号
     * @param activity  活动信息
     */
    void add(String loginStuCode, Activity activity);
}
