package com.moirrra.novel.service.impl;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.resp.HomeBookRespDto;
import com.moirrra.novel.dto.resp.HomeFriendLinkRespDto;
import com.moirrra.novel.manager.cache.HomeBookCacheManager;
import com.moirrra.novel.manager.cache.HomeFriendLinkCacheManager;
import com.moirrra.novel.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-17
 * @Description: 首页模块 服务接口实现类
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeBookCacheManager homeBookCacheManager;

    private final HomeFriendLinkCacheManager friendLinkCacheManager;

    @Override
    public RestResp<List<HomeBookRespDto>> listHomeBooks() {
        return RestResp.ok(homeBookCacheManager.listHomeBooks());
    }

    @Override
    public RestResp<List<HomeFriendLinkRespDto>> listHomeFriendLinks() {
        return RestResp.ok(friendLinkCacheManager.listHomeFriendLinks());
    }
}
