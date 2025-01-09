package com.moirrra.novel.service.impl;

import com.moirrra.novel.core.common.constant.ErrorCodeEnum;
import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dao.entity.AuthorInfo;
import com.moirrra.novel.dao.mapper.AuthorInfoMapper;
import com.moirrra.novel.dto.AuthorInfoDto;
import com.moirrra.novel.dto.req.AuthorRegisterReqDto;
import com.moirrra.novel.manager.cache.AuthorInfoCacheManager;
import com.moirrra.novel.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-24
 * @Description: 作家模块 服务实现类
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorInfoMapper authorInfoMapper;

    private final AuthorInfoCacheManager authorInfoCacheManager;

    @Override
    public RestResp<Void> register(AuthorRegisterReqDto dto) {
        // 检查是否已注册为作家
        AuthorInfoDto author = authorInfoCacheManager.getAuthor(dto.getUserId());
        if (author != null) {
            return RestResp.ok();
        }
        // 保存作家注册信息
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setUserId(dto.getUserId());
        authorInfo.setChatAccount(dto.getChatAccount());
        authorInfo.setEmail(dto.getEmail());
        authorInfo.setInviteCode("0");
        authorInfo.setTelPhone(dto.getTelPhone());
        authorInfo.setPenName(dto.getPenName());
        authorInfo.setWorkDirection(dto.getWorkDirection());
        authorInfo.setCreateTime(LocalDateTime.now());
        authorInfo.setUpdateTime(LocalDateTime.now());
        int count = authorInfoMapper.insert(authorInfo);
        authorInfoCacheManager.evictAuthorCache();
        if (count > 0) {
            return RestResp.ok();
        } else {
            return RestResp.fail(ErrorCodeEnum.AUTHOR_EXIST);
        }
    }

    @Override
    public RestResp<Integer> getStatus(Long userId) {
        AuthorInfoDto author = authorInfoCacheManager.getAuthor(userId);
        if (!Objects.isNull(author)) {
            return RestResp.ok(author.getStatus());
        }
        return RestResp.ok(null);
    }
}
