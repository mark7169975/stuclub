package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.group.AssociationApply;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssociationController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/22 14:13
 * @version: V1.0
 */
@RestController
@RequestMapping("association")
public class AssociationController {
    @Autowired
    private AssociationService associationService;

    /**
     * 增加
     *
     * @param associationApply
     */
    @PostMapping("add")
    public Result add(@RequestBody AssociationApply associationApply) {
        try {
            associationService.add(associationApply);
            return new Result(true, StatusCode.OK, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "增加失败");
        }

    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param rows      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{rows}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int rows) {
        List<AssociationGroup> pageList = associationService.findSearch(searchMap, page, rows);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<AssociationGroup>(associationService.getTotle(),
                pageList));
    }

    @GetMapping("findByTypeCode/{code}")
    public List<Association> findAssByCode(@PathVariable Integer code) {
        return associationService.findAssByCode(code);
    }
}
