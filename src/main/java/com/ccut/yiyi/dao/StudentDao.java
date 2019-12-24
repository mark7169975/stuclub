package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: StudentDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:37
 * @version: V1.0
 */
public interface StudentDao extends JpaRepository<Student,String>,JpaSpecificationExecutor<Student> {
    Student findByStuCode(String stuCode);
}
