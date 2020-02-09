package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.dao.StuAssoDao;
import com.ccut.yiyi.dao.StuRoleDao;
import com.ccut.yiyi.dao.StudentDao;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.StuAsso;
import com.ccut.yiyi.model.StuRole;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.StudentGroup;
import com.ccut.yiyi.service.StudentService;
import org.apache.commons.lang3.StringUtils;
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
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<StudentGroup> findSearch(Map whereMap, int page, int size, Integer assoId) {
        Specification<StuRole> specification = createSpecification(whereMap, assoId);
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

    /**
     * 通过学生id查询一个学生信息
     *
     * @param id
     * @return
     */
    @Override
    public Student findById(Integer id) {
        //访问数据库查询数据
        Optional<Student> byId = studentDao.findById(id);
        //如果插叙出数据为null，则返回null，如果有数据则返回数据
        return byId.orElse(null);
    }

    @Override
    public void deleteById(Integer id, Integer assoId) {
        //通过学生主键id查询学生信息
        Optional<Student> byId = studentDao.findById(id);
        //判断学生信息是否为null
        if (byId.isPresent()) {
            //通过学生学号和社团id查询是否为此社团社长
            Association byStuCodeAndAndAssId = associationDao.findByStuCodeAndAssId(byId.get().getStuCode(), assoId);
            //如果为null，则表示不是此社团社长
            if (org.springframework.util.StringUtils.isEmpty(byStuCodeAndAndAssId)) {
                //删除中间表
                stuRoleDao.deleteByStuCodeAndAssoId(byId.get().getStuCode(), assoId);
                stuAssoDao.deleteByStuCodeAndAssId(byId.get().getStuCode(), assoId);
                //查询此学生是否加入别的社团
                List<StuRole> byStuCode = stuRoleDao.findByStuCode(byId.get().getStuCode());
                //如果判断为null，则未加入别的社团，此学生信息可以删除
                if (byStuCode.isEmpty()) {
                    studentDao.deleteById(id);
                }
            } else {
                //如果是此社团社长，则不能删除，抛出异常
                throw new RuntimeException();
            }

        }

    }

    @Override
    public void update(String oldStuCode, Student student) {
        //如果修改前学号和修改数据的学号一样表示没有修改学号
        if (oldStuCode.equals(student.getStuCode())) {
            //修改数据到数据库
            studentDao.save(student);
        } else {
            //查询之前有没有使用此学号的学生
            if (studentDao.findByStuCode(student.getStuCode()) == null) {
                //通过学号查询学生信息
                Student byStuCode = studentDao.findByStuCode(oldStuCode);
                //判断学生的密码是否为默认
                if (byStuCode.getStuPwd().equals(oldStuCode)) {
                    //如果密码为默认值，则修改密码为新的学号
                    student.setStuPwd(student.getStuCode());
                }
                studentDao.save(student);
                //通过修改前学号查询角色中间表数据，并且修改为新学号
                stuRoleDao.findByStuCode(oldStuCode).forEach(s -> {
                    s.setStuCode(student.getStuCode());
                    stuRoleDao.save(s);
                });
                //通过修改前学号查询社团中间表数据，并且修改为新学号
                stuAssoDao.findByStuCode(oldStuCode).forEach(s -> {
                    s.setStuCode(student.getStuCode());
                    stuAssoDao.save(s);
                });
                List<Association> byStuCode1 = associationDao.findByStuCode(oldStuCode);
                //判断此学生是不是社团的社长
                if (!byStuCode1.isEmpty()) {
                    byStuCode1.forEach(s -> {
                        s.setStuCode(student.getStuCode());
                        associationDao.save(s);
                    });
                }
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void updateManage(String stuCode, Integer assId, Integer sign) {

        StuRole byAssoIdAndStuCode = stuRoleDao.findByAssoIdAndStuCode(assId, stuCode);
        if (!org.springframework.util.StringUtils.isEmpty(byAssoIdAndStuCode)) {
            //sign 为1时，设置为管理人员，为0时，取消管理
            if (sign == 1) {
                byAssoIdAndStuCode.setRoleCode(2001);
                stuRoleDao.save(byAssoIdAndStuCode);
            }
            if (sign == 0) {
                byAssoIdAndStuCode.setRoleCode(2002);
                stuRoleDao.save(byAssoIdAndStuCode);
            }

        }
    }

    /**
     * 动态条件构建
     *
     * @param searchMap 查询条件
     * @param assoId    社团id
     * @return 返回数据
     */
    private Specification<StuRole> createSpecification(Map searchMap, Integer assoId) {

        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //添加查询条件，学生加入社团id为assoId的所有学生
            predicateList.add(cb.equal(root.get("assoId"), assoId));

            // 添加输出的查询条件
            if (searchMap.get("search") != null && !"".equals(searchMap.get("search"))) {
                //判断是否是数字组成的字符串
                if (StringUtils.isNumeric((String) searchMap.get("search"))) {
                    predicateList.add(cb.like(root.get("stuCode").as(String.class), "%" + searchMap.get
                            ("search") + "%"));
                } else {
                    //通过姓名查询未完成
                    List<StuRole> byAssoId = stuRoleDao.findByAssoId(assoId);
                }
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }
}
