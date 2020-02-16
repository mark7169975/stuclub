package com.ccut.yiyi.service.impl;

import com.ccut.yiyi.dao.AssociationDao;
import com.ccut.yiyi.dao.NoticeDao;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.Notice;
import com.ccut.yiyi.model.group.NoticeGroup;
import com.ccut.yiyi.service.AssociationService;
import com.ccut.yiyi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @ClassName: NoticeServiceImpl    公告服务层实现
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/15 20:12
 * @version: V1.0
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private AssociationService associationService;
    @Autowired
    private AssociationDao associationDao;

    @Override
    public void add(String stuCode, Notice notice) {
        notice.setNotPerson(stuCode);
        noticeDao.save(notice);
    }

    @Override
    public Page<NoticeGroup> findSearch(Map searchMap, int page, int rows, String stuCode, String role) {
        //通过学号和角色信息，获取所管理的社团信息
        List<Association> byStuCodeAndRole = associationService.findByStuCodeAndRole(stuCode, role);
        //判断查询出来的数据是否为null
        if (!byStuCodeAndRole.isEmpty()) {
            ArrayList<Integer> integers = new ArrayList<>();
            //把查询出来的社团信息的社团id放在list集合里
            byStuCodeAndRole.forEach(association -> integers.add(association.getAssId()));
            //调用动态构建条件的方法
            Specification<Notice> specification = createSpecification(searchMap, integers);
            PageRequest pageRequest = PageRequest.of(page - 1, rows, Sort.Direction.DESC, "createTime");
            return noticeDao.findAll(specification, pageRequest).map(notice -> {
                NoticeGroup noticeGroup = new NoticeGroup();
                noticeGroup.setNotice(notice);
                Optional<Association> byId = associationDao.findById(notice.getAssId());
                byId.ifPresent(noticeGroup::setAssociation);
                return noticeGroup;
            });
        }
        return null;
    }

    @Override
    public Notice findOne(Integer id) {
        //通过公告id查询数据库信息
        Optional<Notice> byId = noticeDao.findById(id);
        //如果查询结果不为nul，则返回数据，为null，则返回null
        return byId.orElse(null);
    }

    @Override
    public void update(String stuCode, Notice notice) {
        //修改修改公告信息的管理员信息
        notice.setNotPerson(stuCode);
        //修改时间
        notice.setCreateTime(new Date());
        noticeDao.save(notice);
    }

    @Override
    public void delete(Integer notId) {
        //通过id查询公告信息
        Optional<Notice> byId = noticeDao.findById(notId);
        //判断是否为null，不为null则删除
        byId.ifPresent(notice -> noticeDao.delete(notice));

    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Notice> createSpecification(Map searchMap, List<Integer> integers) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //判断integers是不是为null
            if (!integers.isEmpty()) {
                //获取数据库中assId的值在integers中的数据
                CriteriaBuilder.In<Integer> assId = cb.in(root.get("assId"));
                integers.forEach(assId::value);
                predicateList.add(assId);
            }
            // 公告标题
            if (searchMap.get("not_title") != null && !"".equals(searchMap.get("not_title"))) {
                predicateList.add(cb.like(root.get("not_title").as(String.class), "%" + (String) searchMap.get
                        ("not_title") + "%"));
            }
            // 公告内容
            if (searchMap.get("not_content") != null && !"".equals(searchMap.get("not_content"))) {
                predicateList.add(cb.like(root.get("not_content").as(String.class), "%" + (String) searchMap.get
                        ("not_content") + "%"));
            }
            // 公告人学号
            if (searchMap.get("not_person") != null && !"".equals(searchMap.get("not_person"))) {
                predicateList.add(cb.like(root.get("not_person").as(String.class), "%" + (String) searchMap.get
                        ("not_person") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }
}
