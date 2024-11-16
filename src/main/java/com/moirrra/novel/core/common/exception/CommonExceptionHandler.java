package com.moirrra.novel.core.common.exception;

import com.moirrra.novel.core.common.constant.ErrorCodeEnum;
import com.moirrra.novel.core.common.resp.RestResp;
import lombok.extern.slf4j.Slf4j;

import java.net.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-07
 * @Description:
 * @Version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * 处理数据校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public RestResp<Void> handlerBindException(BindException e) {
        log.error(e.getMessage(), e);
        return RestResp.fail(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR);
    }

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public RestResp<Void> handlerBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return RestResp.fail(e.getErrorCodeEnum());
    }

    /**
     * 处理系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestResp<Void> handlerBusinessException(Exception e) {
        log.error(e.getMessage(), e);
        return RestResp.error();
    }
}
