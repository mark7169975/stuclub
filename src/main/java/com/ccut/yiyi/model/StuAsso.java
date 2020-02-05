package com.ccut.yiyi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: StuAsso  学生和社团中间表
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/5 18:28
 * @version: V1.0
 */
@Entity
@Data
@Table(name="stu_asso")
public class StuAsso implements Serializable {
    @Id
    @Column(name="stu_asso_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer stuAssoId;//

    @Column(name="stu_code")
    private String stuCode;//学号

    @Column(name="ass_id")
    private Integer assId;//社团id
}
