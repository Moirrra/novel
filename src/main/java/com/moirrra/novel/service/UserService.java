package com.moirrra.novel.service;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.req.UserLoginReqDto;
import com.moirrra.novel.dto.req.UserRegisterReqDto;
import com.moirrra.novel.dto.resp.UserLoginRespDto;
import com.moirrra.novel.dto.resp.UserRegisterRespDto;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 用户模块服务接口
 * @Version: 1.0
 */

public interface UserService {

    RestResp<UserRegisterRespDto> register(UserRegisterReqDto reqDto);

    RestResp<UserLoginRespDto> login(UserLoginReqDto reqDto);
}
