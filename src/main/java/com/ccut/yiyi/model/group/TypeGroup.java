package com.ccut.yiyi.model.group;

import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.AssociationType;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName: TypeGroup
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/23 13:32
 * @version: V1.0
 */
@Data
@ToString
public class TypeGroup {
    private AssociationType associationType;
    private List<Association> associationList;
}
