package com.moirrra.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moirrra.novel.core.common.constant.ErrorCodeEnum;
import com.moirrra.novel.core.common.exception.BusinessException;
import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.core.constant.SystemConfigConsts;
import com.moirrra.novel.core.util.CommunityUtils;
import com.moirrra.novel.core.util.JwtUtils;
import com.moirrra.novel.dao.entity.UserInfo;
import com.moirrra.novel.dao.mapper.UserInfoMapper;
import com.moirrra.novel.dto.req.UserLoginReqDto;
import com.moirrra.novel.dto.req.UserRegisterReqDto;
import com.moirrra.novel.dto.resp.UserLoginRespDto;
import com.moirrra.novel.dto.resp.UserRegisterRespDto;
import com.moirrra.novel.manager.redis.VerifyCodeManager;
import com.moirrra.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 用户模块服务实现类
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VerifyCodeManager verifyCodeManager;

    private final UserInfoMapper userInfoMapper;

    private final JwtUtils jwtUtils;

    @Override
    public RestResp<UserRegisterRespDto> register(UserRegisterReqDto reqDto) {
        // 校验验证码
        if (!verifyCodeManager.imgVerifyCodeOk(reqDto.getSessionId(), reqDto.getVelCode())) {
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }

        // 校验手机号是否已注册
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", reqDto.getUsername())
                .last("limit 1");
        if (userInfoMapper.selectCount(queryWrapper) > 0) {
            // 手机号已注册
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }

        // 保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(reqDto.getUsername());
        userInfo.setSalt(CommunityUtils.generateUUID().substring(0,5));
        userInfo.setPassword(CommunityUtils.md5(reqDto.getPassword() + userInfo.getSalt()));
        userInfo.setNickName(userInfo.getUsername());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfoMapper.insert(userInfo);

        // 删除验证码
        verifyCodeManager.removeImgVerifyCode(reqDto.getSessionId());

        // 生成JWT并返回
        return RestResp.ok(
                UserRegisterRespDto.builder()
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                        .uid(userInfo.getId())
                        .build()
        );
    }

    @Override
    public RestResp<UserLoginRespDto> login(UserLoginReqDto reqDto) {
        // 查询用户
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", reqDto.getUsername())
                .last("limit 1");
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (Objects.isNull(userInfo)) {
            // 用户不存在
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }

        // 判断密码
        if (!Objects.equals(userInfo.getPassword(), CommunityUtils.md5(reqDto.getPassword() + userInfo.getSalt()))) {
            // 密码错误
            throw new BusinessException(ErrorCodeEnum.USER_PASSWORD_ERROR);
        }

        // 生成JWT并返回
        return RestResp.ok(UserLoginRespDto.builder()
                .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                .uid(userInfo.getId())
                .nickName(userInfo.getNickName())
                .build());
    }
}
