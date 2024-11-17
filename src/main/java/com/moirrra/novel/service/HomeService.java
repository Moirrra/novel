package com.moirrra.novel.service;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.resp.HomeBookRespDto;
import com.moirrra.novel.dto.resp.HomeFriendLinkRespDto;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-17
 * @Description: 首页模块 服务接口
 * @Version: 1.0
 */

public interface HomeService {
    /**
     * 查询首页小说推荐列表
     * @return 首页小说推荐列表的rest响应结果
     */
    RestResp<List<HomeBookRespDto>> listHomeBooks();

    /**
     *
     * @return
     */
    RestResp<List<HomeFriendLinkRespDto>> listHomeFriendLinks();
}
