package com.ccut.yiyi.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: StuRole  学生和角色中间表
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/5 18:35
 * @version: V1.0
 */
@Entity
@Data
@Table(name="stu_role")
@ToString
public class StuRole implements Serializable {
    @Id
    @Column(name="stu_role_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer stuRoleId;//


    @Column(name="stu_code")
    private String stuCode;//学号

    @Column(name="role_code")
    private Integer roleCode;//社团id

    @Column(name="asso_id")
    private Integer assoId;//社团id
}
