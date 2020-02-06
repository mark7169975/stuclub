package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.group.AssociationApply;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssociationController 社团管理的Controller
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
     * 社团申请Controller
     *
     * @param associationApply
     */
    @PostMapping("add")
    public Result add(@RequestBody AssociationApply associationApply) {
        try {
            associationService.add(associationApply);
            //成功返回成功数据
            return new Result(true, StatusCode.OK, "增加成功");
        } catch (Exception e) {
            //报错返回失败，打印错误信息
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "增加失败");
        }
    }
    /**
     * 社团查询，分页+多条件查询，默认查询条件为null
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param rows      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{rows}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int rows) {
        try {
            Page<AssociationGroup> pageList = associationService.findSearch(searchMap, page, rows);
            //查询成功返回成功数据
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(),
                    pageList.getContent()));
        } catch (Exception e) {
            //查询失败返回失败数据，并在控制台打印错误信息
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
    }
    /**
     * 查询通过社团类型typeCode下的所有社团
     * @param code
     * @return
     */
    @GetMapping("findByTypeCode/{code}")
    public List<Association> findAssByCode(@PathVariable Integer code) {
        return associationService.findAssByCode(code);
    }

    /**
     * 根据社团ID查询一个社团
     * @param id ID
     * @return
     */
    @GetMapping("findOne/{id}")
    public AssociationGroup findOne(@PathVariable Integer id) {
        return associationService.findOne(id);
    }
    /**
     * 删除社团信息
     * @param id
     */
    @DeleteMapping("delete/{id}/{stuCode}")
    public Result delete(@PathVariable Integer id,@PathVariable String stuCode) {
        try {
            associationService.deleteById(id,stuCode);
            return new Result(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "删除失败");
        }

    }

    /**
     * 修改社团信息
     * @param associationGroup
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody AssociationGroup associationGroup){
        try {
            associationService.update(associationGroup);
            return new Result(true,StatusCode.OK,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"修改失败");
        }

    }
}
