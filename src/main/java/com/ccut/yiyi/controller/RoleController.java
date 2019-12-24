package com.ccut.yiyi.controller;

import com.ccut.yiyi.model.Role;
import com.ccut.yiyi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: RoleController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/23 12:38
 * @version: V1.0
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping("findAll")
    public List<Role> findAll() {
        return roleService.findAll();
    }
}
