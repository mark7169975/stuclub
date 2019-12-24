package com.ccut.yiyi.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: AssociationType 社团类型实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:19
 * @version: V1.0
 */
@Entity
@Table(name="association_type")
@Data
@ToString
public class AssociationType implements Serializable {
    @Id
    @Column(name="type_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer typeId;//


    @Column(name="type_name")
    private String typeName;//社团类型名称

    @Column(name="type_code")
    private Integer typeCode;//类型编号
}
