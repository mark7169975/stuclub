package com.ccut.yiyi.model.group;

import com.ccut.yiyi.model.Activity;
import com.ccut.yiyi.model.Association;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: ActivityGroup
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/18 21:25
 * @version: V1.0
 */
@Data
@ToString
public class ActivityGroup implements Serializable {
    private Activity activity;
    private Association association;
}
