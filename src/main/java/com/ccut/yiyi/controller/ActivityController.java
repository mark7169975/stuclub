package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.model.group.ActivityGroup;
import com.ccut.yiyi.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping(value = {"/add/{loginStuCode}/{remain}", "/add/{loginStuCode}"})
    public Result add(@PathVariable String loginStuCode, @PathVariable(value = "remain", required = false) Integer[]
            remain, @RequestBody Activity activity) {
        try {
            if (remain != null) {
                activityService.add(loginStuCode, remain, activity);
            } else {
                activityService.add(loginStuCode, activity);
            }
            return new Result(true, StatusCode.OK, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, e.getMessage());
        }

    }

    /**
     * 活动审核的查询 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param rows      页大小
     * @return 分页结果
     */
    @PostMapping("/searchApproval/{page}/{rows}")
    public Result searchApproval(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int rows) {
        try {
            Page<ActivityGroup> pageList = activityService.searchApproval(searchMap, page, rows);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(),
                    pageList.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }

    }

    /**
     * 查询管理员所管理社团的活动列表 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param rows      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{rows}/{stuCode}/{role}")
    public Result findSearch(@PathVariable int page, @PathVariable int rows,
                             @PathVariable String stuCode, @PathVariable String role, @RequestBody Map searchMap) {
        try {
            Page<ActivityGroup> pageList = activityService.findSearch(searchMap, page, rows, stuCode, role);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }

    }

    @PostMapping("changeApproval/{actId}/{mark}")
    public Result changeApproval(@PathVariable Integer actId, @PathVariable Integer mark) {
        try {
            activityService.changeApproval(actId, mark);
            return new Result(true, StatusCode.OK, "修改审批状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改状态失败");
        }
    }

    /**
     * 重新申请活动
     *
     * @param actId 活动id
     */
    @PostMapping("reapply/{actId}")
    public Result reapply(@PathVariable Integer actId) {
        try {
            activityService.reapply(actId);
            return new Result(true, StatusCode.OK, "重新申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "重新申请失败");
        }
    }

    /**
     * 查询一个活动详细信息
     * @param id    活动id
     */
    @GetMapping("findOne/{id}")
    public ActivityGroup findOne(@PathVariable Integer id) {
        return activityService.findOne(id);
    }

    /**
     * 通过活动id删除活动信息
     * @param id    活动id
     */
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        try {
            activityService.delete(id);
            return new Result(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "删除失败");
        }
    }

}
