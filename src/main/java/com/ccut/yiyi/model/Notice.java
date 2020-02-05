package com.ccut.yiyi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Notice 公告实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:26
 * @version: V1.0
 */
@Entity
@Data
@Table(name = "notice")
public class Notice implements Serializable {
    @Id
    @Column(name = "not_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notId;//公告id

    @Column(name = "not_title")
    private String notTitle;//公告标题

    @Column(name = "not_content")
    private String notContent;//公告内容

    @Column(name = "not_person")
    private String notPerson;//公告人学号

    @Column(name = "create_time")
    private Date createTime;//创建时间

    @Column(name = "ass_id")
    private Integer assId;//公告所属社团
}
