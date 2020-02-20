package com.ccut.yiyi.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: ActAsset
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/18 12:33
 * @version: V1.0
 */
@Entity
@Data
@Table(name = "act_asset")
@ToString
public class ActAsset implements Serializable {
    @Id
    @Column(name = "act_asset_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actAssetId;//

    @Column(name = "act_id")
    private Integer actId;//活动id

    @Column(name = "asset_id")
    private Integer assetId;//资产id

    @Column(name = "number")
    private Integer number;//租借数量
}
