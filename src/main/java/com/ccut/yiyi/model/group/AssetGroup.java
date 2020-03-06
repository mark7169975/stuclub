package com.ccut.yiyi.model.group;

import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.model.Association;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: AssetGroup
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/3/6 13:39
 * @version: V1.0
 */
@Data
@ToString
public class AssetGroup implements Serializable {
    private Activity activity;
    private Association association;
    private List<AssetLeaseInfo> assetLeaseInfoList;

}
