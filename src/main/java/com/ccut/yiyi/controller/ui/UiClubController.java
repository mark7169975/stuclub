package com.ccut.yiyi.controller.ui;

import com.ccut.yiyi.common.PageResult;
import com.ccut.yiyi.common.Result;
import com.ccut.yiyi.common.StatusCode;
import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.Student;
import com.ccut.yiyi.model.group.AssociationGroup;
import com.ccut.yiyi.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: UiClubController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/3/4 16:53
 * @version: V1.0
 */
@RestController
@RequestMapping("ui/association")
public class UiClubController {
    @Autowired
    private AssociationService associationService;
    /**
     * 社团查询，分页+多条件查询，默认查询条件为null
     *
     * @param page      页码
     * @param rows      页大小
     * @return 分页结果
     */
    @GetMapping ("/search/{page}/{rows}/{id}")
    public Result findSearch( @PathVariable int page, @PathVariable int rows,@PathVariable int id) {
        try {
            Page<Association> pageList = associationService.findSearch(page, rows,id);
            //查询成功返回成功数据
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(),
                    pageList.getContent()));
        } catch (Exception e) {
            //查询失败返回失败数据，并在控制台打印错误信息
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
    }
    @GetMapping("/queryClubMess/{assId}")
    public AssociationGroup queryClubMess(@PathVariable Integer assId){
        return associationService.findOne(assId);
    }
    @GetMapping("/queryClubManage/{assId}")
    public List<Student> queryClubManage(@PathVariable Integer assId){
        return associationService.findAllManage(assId);
    }
}
