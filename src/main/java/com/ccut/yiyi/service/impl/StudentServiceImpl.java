package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.dao.StudentDao;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.AssociationGroup;
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
import java.util.Optional;

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
    @Autowired
    private AssociationDao associationDao;

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<AssociationGroup> findSearch(Map whereMap, int page, int size) {
        Specification<Student> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        //把student分页数据转换成组合类分页数据
        return studentDao.findAll(specification, pageRequest).map(s -> {
            AssociationGroup associationGroup = new AssociationGroup();
            associationGroup.setStudent(s);
          //  Optional<Association> associationDaoById = associationDao.findById(s.getAssociationId());
          //  associationDaoById.ifPresent(associationGroup::setAssociation);
            return associationGroup;
        });
    }

    @Override
    public void add(Student student) {
        student.setStuPwd(student.getStuCode());
        studentDao.save(student);
    }

    @Override
    public AssociationGroup findById(Integer id) {
        Optional<Student> byId = studentDao.findById(id);
        if(byId.isPresent()){
            Student student = byId.get();
            AssociationGroup associationGroup = new AssociationGroup();
            associationGroup.setStudent(student);
           /* Optional<Association> byId1 = associationDao.findById(student.getAssociationId());
            if(byId1.isPresent()){
                Association association = byId1.get();
                associationGroup.setAssociation(association);
                associationGroup.setAssociation(association);
                return associationGroup;
            }*/

        }
return null;
    }

    @Override
    public void deleteById(Integer id) {
        studentDao.deleteById(id);
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
