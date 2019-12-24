package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: StudentController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 17:12
 * @version: V1.0
 */
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param rows      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{rows}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int rows) {
        Page<Student> pageList = studentService.findSearch(searchMap, page, rows);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList
                .getContent()));
    }

    /**
     * 增加
     *
     * @param student
     */
    @PostMapping("add")
    public Result add(@RequestBody Student student) {
        try {
            System.out.println(student);
            studentService.add(student);
            return new Result(true, StatusCode.OK, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "添加失败");
        }

    }
}