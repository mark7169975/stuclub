package com.ccut.yiyi.model.group;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AssociationApply 社团申请组合实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/22 14:01
 * @version: V1.0
 */
@Data
@ToString
public class AssociationApply implements Serializable {
    private String assName;
    private String stuCode;
    private String stuName;
    private String stuCollege;
    private String stuMajor;
    private String assDescription;
    private Integer typeCode;
}
