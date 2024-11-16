package com.moirrra.novel.core.common.exception;

import com.moirrra.novel.core.common.constant.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-07
 * @Description: 自定义业务异常
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true) // 在生成equals和hashCode方法时，会包含父类的属性
@Data
public class BusinessException extends RuntimeException {

    private final ErrorCodeEnum errorCodeEnum;

    public BusinessException(final ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage(), null, false, false);
        this.errorCodeEnum = errorCodeEnum;
    }
}
