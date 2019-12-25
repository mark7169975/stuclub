package com.ccut.yiyi.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: Student 学生实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/15 20:08
 * @version: V1.0
 */
@Data
@ToString
@Entity
@Table(name="student")
public class Student {
    @Id
    @Column(name="stu_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer stuId;//


    @Column(name="stu_name")
    private String stuName;//学生姓名

    @Column(name="stu_code")
    private String stuCode;//学号

    @Column(name="stu_birthday")
    private java.util.Date stuBirthday;//生日

    @Column(name="stu_sex")
    private Integer stuSex;//性别：0女 1男

    @Column(name="stu_email")
    private String stuEmail;//邮件

    @Column(name="stu_tel")
    private String stuTel;//电话

    @Column(name="stu_qq")
    private String stuQq;//QQ

    @Column(name="stu_college")
    private String stuCollege;//学院

    @Column(name="stu_major")
    private String stuMajor;//专业

    @Column(name="stu_avatar")
    private String stuAvatar;//头像

    @Column(name="stu_pwd")
    private String stuPwd;//密码

    @Column(name="stu_description")
    private String stuDescription;//个人简介

    @Column(name="created_time")
    private Date createdTime;//

    @Column(name="association_id")
    private Integer associationId;//社团id

    @Column(name="role_code")
    private Integer roleCode;//角色编号
}
