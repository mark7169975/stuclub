package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Notice;
import com.ccut.yiyi.model.group.NoticeGroup;
import com.ccut.yiyi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: NoticeController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 20:08
 * @version: V1.0
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 添加公告信息
     *
     * @param loginStuCode 登录人学号
     * @param notice       添加信息
     */
    @PostMapping("/add/{loginStuCode}")
    public Result add(@PathVariable String loginStuCode, @RequestBody Notice notice) {
        try {
            noticeService.add(loginStuCode, notice);
            return new Result(true, StatusCode.OK, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "添加失败");
        }
    }

    /**
     * 分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param rows      条数
     * @param stuCode   学号
     * @param role      角色
     */
    @PostMapping("search/{page}/{rows}/{stuCode}/{role}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int rows,
                             @PathVariable String stuCode, @PathVariable String role) {
        try {
            Page<NoticeGroup> pageList = noticeService.findSearch(searchMap, page, rows, stuCode, role);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }

    }

    /**
     * 查询一个公告信息
     *
     * @param id 公告id
     */
    @GetMapping("/findOne/{id}")
    public Notice findOne(@PathVariable Integer id) {
        return noticeService.findOne(id);
    }

    /**
     * 修改公告内容
     *
     * @param stuCode 修改人学号
     * @param notice  修改内容
     */
    @PostMapping("update/{stuCode}")
    public Result update(@PathVariable String stuCode, @RequestBody Notice notice) {
        try {
            noticeService.update(stuCode, notice);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }

    /**
     * 根据公告id删除公告信息
     *
     * @param notId 公告id
     */
    @DeleteMapping("/delete/{notId}")
    public Result delete(@PathVariable Integer notId) {
        try {
            noticeService.delete(notId);
            return new Result(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "删除失败");
        }
    }
}
