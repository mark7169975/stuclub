package com.ccut.yiyi.model.group;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: StudentSimplify
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/9 14:34
 * @version: V1.0
 */
@Data
@ToString
public class StudentSimplify implements Serializable {
    private String id;
    private String text;
}
