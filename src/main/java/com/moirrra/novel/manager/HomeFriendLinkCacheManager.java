package com.moirrra.novel.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moirrra.novel.core.constant.CacheConsts;
import com.moirrra.novel.dao.entity.HomeFriendLink;
import com.moirrra.novel.dao.mapper.HomeFriendLinkMapper;
import com.moirrra.novel.dto.resp.HomeFriendLinkRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-17
 * @Description:
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class HomeFriendLinkCacheManager {

    private final HomeFriendLinkMapper friendLinkMapper;

    /**
     * 查询友情链接，并放入缓存
     * @return
     */
    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER, value = CacheConsts.HOME_FRIEND_LINK_CACHE_NAME)
    public List<HomeFriendLinkRespDto> listHomeFriendLinks() {
        QueryWrapper<HomeFriendLink> wrapper = new QueryWrapper<HomeFriendLink>()
                .eq("is_open", 1)
                .orderByAsc("sort");
        List<HomeFriendLink> list = friendLinkMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            return list.stream().map(v -> {
                HomeFriendLinkRespDto respDto = new HomeFriendLinkRespDto();
                respDto.setLinkName(v.getLinkName());
                respDto.setLinkUrl(v.getLinkUrl());
                return respDto;
            }).toList();
        }
        return Collections.emptyList();
    }
}
