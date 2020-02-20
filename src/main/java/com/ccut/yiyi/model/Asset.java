package com.ccut.yiyi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: Asset 资产实体类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/18 16:14
 * @version: V1.0
 */
@Entity
@Table(name = "asset")
@Data
public class Asset implements Serializable {
    @Id
    @Column(name = "asset_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assetId;//资产id

    @Column(name = "asset_name")
    private String assetName;//资产名称

    @Column(name = "asset_number")
    private Integer assetNumber;//总数量

    @Column(name = "asset_borrow")
    private Integer assetBorrow;//已借出数量

    @Column(name = "asset_remain")
    private Integer assetRemain;//剩余数量

    @Column(name = "asset_unit")
    private String assetUnit;//单位

    @Column(name = "asset_site")
    private String assetSite;//资产地点

}
