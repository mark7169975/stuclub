package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.*;
import com.ccut.yiyi.model.*;
import com.ccut.yiyi.model.group.AssociationApply;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName: AssociationServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/22 14:47
 * @version: V1.0
 */
@Service
@Transactional
public class AssociationServiceImpl implements AssociationService {
    @Autowired
    private AssociationDao associationDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private AssociationTypeDao associationTypeDao;
    @Autowired
    private StuAssoDao stuAssoDao;
    @Autowired
    private StuRoleDao stuRoleDao;

    /**
     * @param associationApply
     * 申请社团的service实现
     */
    @Override
    public void add(AssociationApply associationApply) {
        //设置社团的信息
        Association association = new Association();
        association.setAssName(associationApply.getAssName());
        association.setStuCode(associationApply.getStuCode());
        association.setAssDescription(associationApply.getAssDescription());
        association.setTypeCode(associationApply.getTypeCode());
        //添加社团到数据库
        Association save = associationDao.save(association);
        //设置社长的学生信息
        Student student = new Student();
        student.setStuCode(associationApply.getStuCode());
        student.setStuName(associationApply.getStuName());
        student.setStuCollege(associationApply.getStuCollege());
        student.setStuMajor(associationApply.getStuMajor());
        student.setStuEmail(associationApply.getStuEmail());
        student.setStuQq(associationApply.getStuQq());
        student.setStuTel(associationApply.getStuTel());
        //设置默认密码为学号
        student.setStuPwd(associationApply.getStuCode());
        //添加学生信息到数据库
        studentDao.save(student);
        //设置学生和社团的对应关系 多对多
        StuAsso stuAsso = new StuAsso();
        stuAsso.setAssId(save.getAssId());
        stuAsso.setStuCode(student.getStuCode());
        //添加学生和社团的对应关系到数据库
        stuAssoDao.save(stuAsso);
        //设置学生和社团的角色关系 多对多
        StuRole stuRole = new StuRole();
        stuRole.setStuCode(student.getStuCode());
        //创建社团 默认权限为社团管理员 社团管理编号为2001
        stuRole.setRoleCode(2001);
        //设置对应管理的社团id
        stuRole.setAssoId(save.getAssId());
        stuRoleDao.save(stuRole);
    }

    Long total = 0L;

    @Override
    public Page<AssociationGroup> findSearch(Map whereMap, int page, int size) {
        Specification<Association> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return associationDao.findAll(specification, pageRequest).map(ass -> {
            AssociationGroup associationGroup = new AssociationGroup();
            associationGroup.setAssociation(ass);
            associationGroup.setStudent(studentDao.findByStuCode(ass.getStuCode()));
            associationGroup.setAssociationType(associationTypeDao.findByTypeCode(ass.getTypeCode()));
            return associationGroup;
        });
    }

    @Override
    public Long getTotle() {
        return total;
    }

    @Override
    public List<Association> findAssByCode(Integer code) {
        return associationDao.findByTypeCode(code);
    }

    @Override
    public AssociationGroup findOne(Integer id) {

        Optional<Association> byId = associationDao.findById(id);
        //判断是否为空
        if (byId.isPresent()) {
            Association association = byId.get();
            Student student = studentDao.findByStuCode(association.getStuCode());
            AssociationType type = associationTypeDao.findByTypeCode(association.getTypeCode());
            AssociationGroup associationGroup = new AssociationGroup();
            associationGroup.setStudent(student);
            associationGroup.setAssociation(association);
            associationGroup.setAssociationType(type);
            return associationGroup;
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        associationDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Association> createSpecification(Map searchMap) {

        return new Specification<Association>() {

            @Override
            public Predicate toPredicate(Root<Association> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 社团名称
                if (searchMap.get("ass_name") != null && !"".equals(searchMap.get("ass_name"))) {
                    predicateList.add(cb.like(root.get("ass_name").as(String.class), "%" + (String) searchMap.get
                            ("ass_name") + "%"));
                }
                // 社长学号
                if (searchMap.get("stu_code") != null && !"".equals(searchMap.get("stu_code"))) {
                    predicateList.add(cb.like(root.get("stu_code").as(String.class), "%" + (String) searchMap.get
                            ("stu_code") + "%"));
                }
                // 社团徽章
                if (searchMap.get("ass_avatar") != null && !"".equals(searchMap.get("ass_avatar"))) {
                    predicateList.add(cb.like(root.get("ass_avatar").as(String.class), "%" + (String) searchMap.get
                            ("ass_avatar") + "%"));
                }
                // 社团描述
                if (searchMap.get("ass_description") != null && !"".equals(searchMap.get("ass_description"))) {
                    predicateList.add(cb.like(root.get("ass_description").as(String.class), "%" + (String) searchMap
                            .get("ass_description") + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }
}
