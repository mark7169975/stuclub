package com.ccut.yiyi.model.group;

import com.ccut.yiyi.model.StuRole;
import com.ccut.yiyi.model.Student;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: StudentGroup
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/6 21:10
 * @version: V1.0
 */
@Data
@ToString
public class StudentGroup implements Serializable {
    private Student student;
    private StuRole stuRole;
}
