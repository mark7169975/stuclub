package com.ccut.yiyi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Email 邮件实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:21
 * @version: V1.0
 */
@Entity
@Data
@Table(name = "email")
public class Email implements Serializable {
    @Id
    @Column(name = "email_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emailId;//

    @Column(name = "email_to")
    private String emailTo;//接受邮件

    @Column(name = "email_from")
    private String emailFrom;//发送邮件

    @Column(name = "email_subject")
    private String emailSubject;//邮件主题

    @Column(name = "email_content")
    private String emailContent;//邮件内容

    @Column(name = "stu_code")
    private String stuCode;//学生学号

    @Column(name = "email_attach")
    private String emailAttach;//邮件附件

    @Column(name = "mark")
    private Integer mark;//是否删除

    @Column(name = "create_time")
    private Date createTime;//创建时间
}
