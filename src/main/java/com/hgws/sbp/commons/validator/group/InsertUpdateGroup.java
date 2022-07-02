package com.hgws.sbp.commons.validator.group;

import javax.validation.GroupSequence;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project springboot-development-platform
 * @datetime 2022-04-13 12:50
 * @description: [校验规则分组·排序执行]
 */
@GroupSequence({Insert.class, Update.class})
public interface InsertUpdateGroup {
}
