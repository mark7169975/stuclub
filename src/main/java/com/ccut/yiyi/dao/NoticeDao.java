package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: NoticeDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:36
 * @version: V1.0
 */
public interface NoticeDao extends JpaRepository<Notice,Integer>,JpaSpecificationExecutor<Notice> {
}
