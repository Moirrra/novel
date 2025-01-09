package com.moirrra.novel.service;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.req.AuthorRegisterReqDto;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-24
 * @Description: 作家模块服务接口
 * @Version: 1.0
 */

public interface AuthorService {

    /**
     * 作者注册
     * @param dto 作者注册请求DTO
     * @return void
     */
    RestResp<Void> register(AuthorRegisterReqDto dto);

    RestResp<Integer> getStatus(Long userId);
}
