package com.ccut.yiyi.service;

import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.StudentGroup;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @ClassName: StudentService
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 17:02
 * @version: V1.0
 */
public interface StudentService {
    /**
     * 查询选中社团所有学生信息
     * 条件查询+分页
     *
     * @param whereMap 查询条件
     * @param page     页码
     * @param size     每页显示条数
     * @param assoId   社团id
     * @return 返回学生分页数据
     */
    Page<StudentGroup> findSearch(Map whereMap, int page, int size, Integer assoId);

    /**
     * 添加社团成员
     *
     * @param studentGroup
     */
    void add(StudentGroup studentGroup);

    /**
     * 根据学生id查询一个学生信息
     *
     * @param id
     * @return
     */
    Student findById(Integer id);

    /**
     * 删除学生信息
     *
     * @param id     学生主键id
     * @param assoId 社团id
     */
    void deleteById(Integer id, Integer assoId);

    /**
     * 修改学生信息
     *
     * @param oldStuCode 修改前的学号
     * @param student    学生信息
     */
    void update(String oldStuCode, Student student);

    /**
     * 修改是否为此社团管理人员
     *
     * @param stuCode 学号
     * @param assId   社团id
     * @param sign    标记 1为修改为管理人员 0为取消管理员
     */
    void updateManage(String stuCode, Integer assId, Integer sign);

    /**
     * 通过学号查询学生信息
     * @param stuCode   学号
     */
    Student findByStuCode(String stuCode);
}
