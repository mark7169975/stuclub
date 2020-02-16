package com.ccut.yiyi.model.group;

import com.ccut.yiyi.model.Association;
import com.ccut.yiyi.model.Notice;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: NoticeGroup
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/16 16:26
 * @version: V1.0
 */
@Data
@ToString
public class NoticeGroup {
    private Notice notice;
    private Association association;
}
