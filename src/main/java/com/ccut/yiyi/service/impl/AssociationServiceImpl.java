package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.*;
import com.ccut.yiyi.model.*;
import com.ccut.yiyi.model.group.AssociationApply;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.model.group.StudentSimplify;
import com.ccut.yiyi.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

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
     *
     * @param code
     * @return
     */
    @Override
    public List<Association> findAssByCode(Integer code) {
        return associationDao.findByTypeCode(code);
    }

    /**
     * 根据社团id查询一个社团的service实现
     *
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
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id, String stuCode) {
        //删除社团信息
        associationDao.deleteById(id);
        //删除学生社团关联信息
        stuAssoDao.deleteByAssId(id);
        //删除学生和角色关联信息
        stuRoleDao.deleteByAssoId(id);
        //判断学生是否加入别的社团，如果没有加入别的社团则删除学生信息
        if (stuAssoDao.findByStuCode(stuCode).isEmpty()) {
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

    @Override
    public List<StudentSimplify> findOneAss(Integer assId) {
        //通过社团id查询社团详细信息
        Optional<Association> byId = associationDao.findById(assId);
        //创建一个学生集合
        ArrayList<StudentSimplify> students = new ArrayList<>();
        //判断是否为null
        if (byId.isPresent()) {
            //通过社团id查询中间表数据
            List<StuRole> byAssoId = stuRoleDao.findByAssoId(assId);
            //通过社长学号和社团id查询一个中间表数据
            StuRole byAssoIdAndStuCode = stuRoleDao.findByAssoIdAndStuCode(assId, byId.get().getStuCode());
            //把社长信息从中间表数据的集合移除
            byAssoId.remove(byAssoIdAndStuCode);
            //遍历中间表数据，拿到中间表数据的学号，查询单个学生信息，放入到学生集合里
            byAssoId.forEach(stuRole -> {
                StudentSimplify studentSimplify = new StudentSimplify();
                Student byStuCode = studentDao.findByStuCode(stuRole.getStuCode());
                studentSimplify.setId(byStuCode.getStuCode());
                studentSimplify.setText(byStuCode.getStuName());
                students.add(studentSimplify);
            });
        }
        return students;
    }

    @Override
    public void changeManage(String stuCode, Integer assId) {
        //通过社团id查询此社团信息
        Optional<Association> byId = associationDao.findById(assId);
        //判断是否为null
        if (byId.isPresent()) {
            //删除旧社长的中间表数据
            stuRoleDao.deleteByStuCodeAndAssoId(byId.get().getStuCode(), assId);
            stuAssoDao.deleteByStuCodeAndAssId(byId.get().getStuCode(), assId);
            //查询旧社长的中间表数据，为了判断是否加入别的社团
            List<StuRole> byStuCode = stuRoleDao.findByStuCode(byId.get().getStuCode());
            //如果查询为null，证明没有加入别的社团
            if (byStuCode.isEmpty()) {
                //如果没有加入别的社团，则删除学生信息
                studentDao.deleteByStuCode(byId.get().getStuCode());
            }
            //修改此社团新社长的学号
            byId.get().setStuCode(stuCode);
            //保存到数据库
            associationDao.save(byId.get());
            //通过学号和社团id查询中间表数据
            StuRole byAssoIdAndStuCode = stuRoleDao.findByAssoIdAndStuCode(assId, stuCode);
            //把权限修改为管理人员
            byAssoIdAndStuCode.setRoleCode(2001);
            //保存到数据库
            stuRoleDao.save(byAssoIdAndStuCode);
        }
    }

    @Override
    public List<Association> findByStuCodeAndRole(String stuCode, String role) {
        //判断角色是不是超级管理员
        if ("ROLE_SUPERADMIN".equals(role)) {
            //如果是超级管理，则返回所有社团信息，排序为通过社团名称升序
            return associationDao.findAll(Sort.by("assName"));
        }
        if ("ROLE_ADMIN".equals(role)) {
            ArrayList<Association> associations = new ArrayList<>();
            //如果是普通管理，则查询此管理所管理的所有社团
            stuRoleDao.findByStuCodeAndRoleCodeNot(stuCode, 2002).forEach(stuRole -> {
                //通过社团id查询社团信息
                Optional<Association> byId = associationDao.findById(stuRole.getAssoId());
                //如果不为null，则加入associations集合
                byId.ifPresent(associations::add);
            });
            //通过社团名称排序，升序
            associations.sort(Comparator.comparingInt(o -> o.getAssName().charAt(0)));
            return associations;
        }
        //如果不是超级管理也不是普通管理，则返回null
        return null;
    }

    /**
     * 动态条件构建
     *
     * @param searchMap 查询的条件
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
