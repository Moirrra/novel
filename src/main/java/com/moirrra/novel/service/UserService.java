package com.moirrra.novel.service;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.req.UserInfoUptReqDto;
import com.moirrra.novel.dto.req.UserLoginReqDto;
import com.moirrra.novel.dto.req.UserRegisterReqDto;
import com.moirrra.novel.dto.resp.UserInfoRespDto;
import com.moirrra.novel.dto.resp.UserLoginRespDto;
import com.moirrra.novel.dto.resp.UserRegisterRespDto;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 用户模块服务接口
 * @Version: 1.0
 */

public interface UserService {

    /**
     * 用户注册
     * @param reqDto 用户注册请求DTO
     * @return 用户注册响应DTO
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto reqDto);

    /**
     * 用户登录
     * @param reqDto 用户登录请求DTO
     * @return 用户登录响应DTO
     */
    RestResp<UserLoginRespDto> login(UserLoginReqDto reqDto);

    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return 用户信息响应DTO
     */
    RestResp<UserInfoRespDto> getUserInfo(Long userId);

    /**
     * 修改用户信息
     * @param dto 用户信息修改 请求DTO
     * @return void
     */
    RestResp<Void> updateUserInfo(UserInfoUptReqDto dto);
}
