package com.ccut.yiyi.controller;

import com.ccut.yiyi.model.AssociationType;
import com.ccut.yiyi.model.group.TypeGroup;
import com.ccut.yiyi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: TypeController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/21 17:09
 * @version: V1.0
 */
@RestController
@RequestMapping("type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping("findAll")
    public List<AssociationType> findAll() {
        return typeService.findAll();
    }

    /**
     * 查询全部社团Group
     *
     * @return
     */
    @GetMapping("findAllTypeGroup")
    public List<TypeGroup> findAllTypeGroup() {
        return typeService.findAllTypeGroup();
    }
}
