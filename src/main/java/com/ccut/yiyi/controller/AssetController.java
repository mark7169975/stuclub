package com.ccut.yiyi.controller;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Asset;
import com.ccut.yiyi.model.group.AssetGroup;
import com.ccut.yiyi.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AssetController  资产的Controller
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/17 11:46
 * @version: V1.0
 */
@RestController
@RequestMapping("/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;

    /**
     * 添加资产信息
     *
     * @param asset 资产信息
     */
    @PostMapping("add")
    public Result add(@RequestBody Asset asset) {
        try {
            assetService.add(asset);
            return new Result(true, StatusCode.OK, "添加成功");
        } catch (Exception e) {
            return new Result(false, StatusCode.ERROR, "添加失败");
        }
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        try {
            Page<Asset> pageList = assetService.findSearch(searchMap, page, size);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(),
                    pageList.getContent()));
        } catch (Exception e) {
            return new Result(false, StatusCode.ERROR, "查询失败");

        }
    }
    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/searchReturn/{page}/{size}")
    public Result searchReturn(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        try {
            Page<AssetGroup> pageList = assetService.searchReturn(searchMap, page, size);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(),
                    pageList.getContent()));
        } catch (Exception e) {
            return new Result(false, StatusCode.ERROR, "查询失败");

        }
    }
    /**
     * 资产归还
     *
     * @param actId 活动id
     */
    @DeleteMapping("assetReturn/{actId}")
    public Result assetReturn(@PathVariable Integer actId) {
        try {
                assetService.assetReturn(actId);
            return new Result(true, StatusCode.OK, "归还成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, e.getMessage());
        }

    }
    /**
     * 删除资产
     *
     * @param id 资产id
     */
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            assetService.deleteById(id);
            return new Result(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, e.getMessage());
        }

    }

    /**
     * 根据ID查询
     *
     * @param id ID
     */
    @GetMapping("/findOne/{id}")
    public Asset findById(@PathVariable Integer id) {
        return assetService.findById(id);
    }

    /**
     * 修改资产信息
     *
     * @param asset 修改的信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody Asset asset) {
        try {
            assetService.update(asset);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改失败");
        }

    }

    /**
     * 查询所有有资源的资产
     *
     */
    @GetMapping("/findAll")
    public List<Asset> findAll() {
        return assetService.findAll();
    }

}
