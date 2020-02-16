package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ActivityController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 14:31
 * @version: V1.0
 */
@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @PostMapping("/add/{loginStuCode}")
    public Result add(@PathVariable String loginStuCode, @RequestBody Activity activity) {
        activityService.add(loginStuCode, activity);
        return null;
    }
}
