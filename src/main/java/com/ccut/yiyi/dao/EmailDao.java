package com.ccut.yiyi.dao;

import com.ccut.yiyi.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: EmailDao
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:36
 * @version: V1.0
 */
public interface EmailDao extends JpaRepository<Email,String>,JpaSpecificationExecutor<Email> {
}
