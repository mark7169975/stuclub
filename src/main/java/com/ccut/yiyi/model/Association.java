package com.ccut.yiyi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: Association 社团实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:17
 * @version: V1.0
 */
@Entity
@Table(name="association")
@Data
public class Association implements Serializable {
    @Id
    @Column(name="ass_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer assId;//



    @Column(name="ass_name")
    private String assName;//社团名称

    @Column(name="stu_code")
    private String stuCode;//社长学号

    @Column(name="ass_avatar")
    private String assAvatar;//社团徽章

    @Column(name="ass_description")
    private String assDescription;//社团描述

    @Column(name="type_code")
    private Integer typeCode;//社团所属类型
}
