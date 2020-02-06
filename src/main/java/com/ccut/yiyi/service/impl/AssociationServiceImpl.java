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

import javax.persistence.criteria.Predicate;
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
     * @param associationApply 申请社团的service实现
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

    /**
     * 社团查询全部，默认查询条件为null，当条件为null时查询所有社团
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
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

    /**
     * 通过社团类型的typeCode查询其下所有社团
     * @param code
     * @return
     */
    @Override
    public List<Association> findAssByCode(Integer code) {
        return associationDao.findByTypeCode(code);
    }

    /**
     * 根据社团id查询一个社团的service实现
     * @param id
     * @return
     */
    @Override
    public AssociationGroup findOne(Integer id) {

        Optional<Association> byId = associationDao.findById(id);
        //判断是否为空
        if (byId.isPresent()) {
            Association association = byId.get();
            //查询社长的学生信息
            Student student = studentDao.findByStuCode(association.getStuCode());
            //查询社团所属类型
            AssociationType type = associationTypeDao.findByTypeCode(association.getTypeCode());
            AssociationGroup associationGroup = new AssociationGroup();
            associationGroup.setStudent(student);
            associationGroup.setAssociation(association);
            associationGroup.setAssociationType(type);
            return associationGroup;
        }
        //如果查询失败返回null
        return null;
    }

    /**
     * 删除社团信息的service实现
     * @param id
     */
    @Override
    public void deleteById(Integer id,String stuCode) {
        //删除社团信息
        associationDao.deleteById(id);
        //删除学生社团关联信息
        stuAssoDao.deleteByAssId(id);
        //删除学生和角色关联信息
        stuRoleDao.deleteByAssoId(id);
        //判断学生是否加入别的社团，如果没有加入别的社团则删除学生信息
        if(stuAssoDao.findBystuCode(stuCode).isEmpty()){
            studentDao.deleteByStuCode(stuCode);
        }
    }
    /**
     * 修改社团信息
     *
     * @param associationGroup
     */
    @Override
    public void update(AssociationGroup associationGroup) {
        //更新数据
        associationGroup.getAssociation().setTypeCode(associationGroup.getAssociationType().getTypeCode());
        associationDao.save(associationGroup.getAssociation());
        studentDao.save(associationGroup.getStudent());
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Association> createSpecification(Map searchMap) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            // 社团名称
            if (searchMap.get("search") != null && !"".equals(searchMap.get("search"))) {
                predicateList.add(cb.like(root.get("assName").as(String.class), "%" + (String) searchMap.get
                        ("search") + "%"));
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }
}
