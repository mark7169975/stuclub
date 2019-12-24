package com.ccut.yiyi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: Role 角色实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:29
 * @version: V1.0
 */
@Entity
@Data
@Table(name="role")
public class Role implements Serializable {
    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer roleId;//

    @Column(name="role_name")
    private String roleName;//角色名称

    @Column(name="role_code")
    private Integer roleCode;//角色编号
}
