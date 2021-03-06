package com.ccut.yiyi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Activity 活动实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 15:52
 * @version: V1.0
 */
@Entity
@Table(name = "activity")
@Data
public class Activity implements Serializable {

    @Id
    @Column(name = "act_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actId;//主键id

    @Column(name = "act_name")
    private String actName; //活动名称

    @Column(name = "ass_id")
    private Integer assId;//社团id

    @Column(name = "stu_code")
    private String stuCode;//学号

    @Column(name = "stu_name")
    private String stuName;//负责人

    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private Date startTime;//开始时间

    @Column(name = "act_address")
    private String actAddress;//活动地点

    @Column(name = "act_description")
    private String actDescription;//活动简介

    @Column(name = "mark")
    private Integer mark;//审核：0未审核 1通过 2未通过

    @Column(name = "asset_sign")
    private Integer assetSign;//资产标记 0未借用资产 1借用资产

}
