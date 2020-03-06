package com.ccut.yiyi.controller.ui;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.group.NoticeGroup;
import com.ccut.yiyi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: NoticeController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/3/3 21:44
 * @version: V1.0
 */
@RestController
@RequestMapping("/ui/notice")
public class UiNoticeController {
    @Autowired
    private NoticeService noticeService;

    /**
     * UI首页查询6个公告信息
     *
     * @return  返回数据
     */
    @GetMapping("findAll")
    public List<NoticeGroup> findAll() {
        return noticeService.findAll();

    }
    /**
     * 分页查询
     *
     * @param page      页码
     * @param rows      条数
     */
    @GetMapping("search/{page}/{rows}")
    public Result findSearch( @PathVariable int page, @PathVariable int rows) {
        try {
            Page<NoticeGroup> pageList = noticeService.findSearch(page, rows);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }

    }
}
