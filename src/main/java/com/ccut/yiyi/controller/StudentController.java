package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.StudentGroup;
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
     * 查询选中社团所有学生信息
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param rows      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{rows}/{assoId}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int rows,
                             @PathVariable Integer assoId) {
        try {
            Page<StudentGroup> pageList = studentService.findSearch(searchMap, page, rows, assoId);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }

    }

    /**
     * 增加社团学生信息
     *
     * @param studentGroup
     */
    @PostMapping("add")
    public Result add(@RequestBody StudentGroup studentGroup) {
        try {
            studentService.add(studentGroup);
            return new Result(true, StatusCode.OK, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "添加失败");
        }
    }

    /**
     * 根据学生id查询一个学生信息
     *
     * @param id ID
     * @return
     */
    @GetMapping("findOne/{id}")
    public Student findOne(@PathVariable Integer id) {
        try {
            return studentService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    @PostMapping("update/{oldStuCode}")
    public Result update(@PathVariable String oldStuCode, @RequestBody Student student) {
        try {
            studentService.update(oldStuCode, student);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改失败,学号冲突");
        }

    }

    /**
     * 删除学生信息
     *
     * @param id 学生主键id
     */
    @DeleteMapping("delete/{id}/{assoId}")
    public Result delete(@PathVariable Integer id, @PathVariable Integer assoId) {
        try {
            studentService.deleteById(id, assoId);
            return new Result(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "删除失败,社团社长不能删除");
        }

    }

    @PostMapping("updateManage/{stuCode}/{assId}/{sign}")
    public Result updateManage(@PathVariable String stuCode, @PathVariable Integer assId, @PathVariable Integer sign) {
        try {
            studentService.updateManage(stuCode, assId, sign);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }
}
