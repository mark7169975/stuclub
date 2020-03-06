package com.ccut.yiyi.model.group;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AssetLeaseInfo
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/3/6 13:46
 * @version: V1.0
 */
@Data
@ToString
public class AssetLeaseInfo implements Serializable {
    private String name;//名称
    private Integer number; //数量
    private String unit;//单位
}
