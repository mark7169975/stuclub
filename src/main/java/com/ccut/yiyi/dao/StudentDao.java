package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: StudentDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:37
 * @version: V1.0
 */
public interface StudentDao extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
    /**
     * 通过学号查询学生信息
     *
     * @param stuCode 学号
     * @return 返回学生信息
     */
    Student findByStuCode(String stuCode);

    /**
     * 通过学号删除学生信息
     *
     * @param stuCode 学号
     */
    void deleteByStuCode(String stuCode);

    /**
     * 通过学生姓名查询相似学生信息
     *
     * @param stuName 学生姓名
     * @return 返回数据
     */
    List<Student> findByStuNameLike(String stuName);

}
