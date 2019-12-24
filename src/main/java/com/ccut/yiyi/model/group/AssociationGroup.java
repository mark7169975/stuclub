package com.ccut.yiyi.model.group;

import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.AssociationType;
import com.ccut.yiyi.model.Student;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AssociationGroup
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/22 16:04
 * @version: V1.0
 */
@Data
@ToString
public class AssociationGroup implements Serializable {
    private Student student;
    private Association association;
    private AssociationType associationType;
}
