package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.StudentDao;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: StudentServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 17:02
 * @version: V1.0
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;


    /**
     * 条件查询+分页
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Student> findSearch(Map whereMap, int page, int size) {
        Specification<Student> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return studentDao.findAll(specification, pageRequest);
    }

    @Override
    public void add(Student student) {
        student.setStuPwd(student.getStuCode());
        studentDao.save(student);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Student> createSpecification(Map searchMap) {

        return new Specification<Student>() {

            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 学生姓名
                if (searchMap.get("stu_name") != null && !"".equals(searchMap.get("stu_name"))) {
                    predicateList.add(cb.like(root.get("stu_name").as(String.class), "%" + (String) searchMap.get
                            ("stu_name") + "%"));
                }
                // 学号
                if (searchMap.get("stu_code") != null && !"".equals(searchMap.get("stu_code"))) {
                    predicateList.add(cb.like(root.get("stu_code").as(String.class), "%" + (String) searchMap.get
                            ("stu_code") + "%"));
                }
                // 邮件
                if (searchMap.get("stu_email") != null && !"".equals(searchMap.get("stu_email"))) {
                    predicateList.add(cb.like(root.get("stu_email").as(String.class), "%" + (String) searchMap
                            .get("stu_email") + "%"));
                }
                // 电话
                if (searchMap.get("stu_tel") != null && !"".equals(searchMap.get("stu_tel"))) {
                    predicateList.add(cb.like(root.get("stu_tel").as(String.class), "%" + (String) searchMap.get
                            ("stu_tel") + "%"));
                }
                // QQ
                if (searchMap.get("stu_qq") != null && !"".equals(searchMap.get("stu_qq"))) {
                    predicateList.add(cb.like(root.get("stu_qq").as(String.class), "%" + (String) searchMap.get
                            ("stu_qq") + "%"));
                }
                // 学院
                if (searchMap.get("stu_college") != null && !"".equals(searchMap.get("stu_college"))) {
                    predicateList.add(cb.like(root.get("stu_college").as(String.class), "%" + (String) searchMap
                            .get("stu_college") + "%"));
                }
                // 专业
                if (searchMap.get("stu_major") != null && !"".equals(searchMap.get("stu_major"))) {
                    predicateList.add(cb.like(root.get("stu_major").as(String.class), "%" + (String) searchMap
                            .get("stu_major") + "%"));
                }
                // 头像
                if (searchMap.get("stu_avatar") != null && !"".equals(searchMap.get("stu_avatar"))) {
                    predicateList.add(cb.like(root.get("stu_avatar").as(String.class), "%" + (String) searchMap
                            .get("stu_avatar") + "%"));
                }
                // 密码
                if (searchMap.get("stu_pwd") != null && !"".equals(searchMap.get("stu_pwd"))) {
                    predicateList.add(cb.like(root.get("stu_pwd").as(String.class), "%" + (String) searchMap.get
                            ("stu_pwd") + "%"));
                }
                // 个人简介
                if (searchMap.get("stu_description") != null && !"".equals(searchMap.get("stu_description"))) {
                    predicateList.add(cb.like(root.get("stu_description").as(String.class), "%" + (String) searchMap
                            .get("stu_description") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }
}
