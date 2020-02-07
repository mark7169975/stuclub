package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.dao.StuAssoDao;
import com.ccut.yiyi.dao.StuRoleDao;
import com.ccut.yiyi.dao.StudentDao;
import com.ccut.yiyi.model.StuAsso;
import com.ccut.yiyi.model.StuRole;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.model.group.StudentGroup;
import com.ccut.yiyi.service.StudentService;
import org.apache.commons.lang3.StringUtils;
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
 * @ClassName: StudentServiceImpl
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 17:02
 * @version: V1.0
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private AssociationDao associationDao;
    @Autowired
    private StuRoleDao stuRoleDao;
    @Autowired
    private StuAssoDao stuAssoDao;

    /**
     * 查询选中社团所有学生信息的实现
     * 条件查询+分页
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<StudentGroup> findSearch(Map whereMap, int page, int size,Integer assoId) {
        Specification<StuRole> specification = createSpecification(whereMap,assoId);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        //把学生和社团中间表查询的数据转换为学生社团表的组合类StudentGroup
        return stuRoleDao.findAll(specification, pageRequest).map(s -> {
            StudentGroup studentGroup = new StudentGroup();
            //设置学生信息
            studentGroup.setStudent(studentDao.findByStuCode(s.getStuCode()));
            //设置中间表信息
            studentGroup.setStuRole(s);
            return studentGroup;
        });
    }

    /**
     * 添加社员的service实现
     *
     * @param studentGroup
     */
    @Override
    public void add(StudentGroup studentGroup) {

        //设置默认密码为学号
        studentGroup.getStudent().setStuPwd(studentGroup.getStudent().getStuCode());
        //设置学生角色关联表对应学号
        studentGroup.getStuRole().setStuCode(studentGroup.getStudent().getStuCode());
        //添加学生角色关联表到数据库
        stuRoleDao.save(studentGroup.getStuRole());
        //给学生，社团关联表设置值
        StuAsso stuAsso = new StuAsso();
        stuAsso.setAssId(studentGroup.getStuRole().getAssoId());
        stuAsso.setStuCode(studentGroup.getStuRole().getStuCode());
        //添加学生，社团关联表到数据库
        stuAssoDao.save(stuAsso);
        //查询学生是否加入过其他社团
        Student byStuCode = studentDao.findByStuCode(studentGroup.getStudent().getStuCode());
        //如果能查询出学生信息证明此学生已经加入别的社团
        if (byStuCode == null) {
            //添加学生数据
            studentDao.save(studentGroup.getStudent());
        }
    }

    @Override
    public AssociationGroup findById(Integer id) {
        Optional<Student> byId = studentDao.findById(id);
        if (byId.isPresent()) {
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
    private Specification<StuRole> createSpecification(Map searchMap,Integer assoId) {

        return new Specification<StuRole>() {

            @Override
            public Predicate toPredicate(Root<StuRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //添加查询条件，学生加入社团id为assoId的所有学生
                predicateList.add(cb.equal(root.get("assoId"), assoId));

                // 添加输出的查询条件
                if (searchMap.get("search") != null && !"".equals(searchMap.get("search"))) {
                    //判断是否是数字组成的字符串
                    if(StringUtils.isNumeric((String)searchMap.get("search"))){
                        predicateList.add(cb.like(root.get("stuCode").as(String.class), "%" + (String) searchMap.get
                                ("search") + "%"));
                    }else {
                        //通过姓名查询未完成
                        List<StuRole> byAssoId = stuRoleDao.findByAssoId(assoId);
                    }
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }
}
