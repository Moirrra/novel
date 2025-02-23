package com.moirrra.novel.controller.front;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dto.resp.NewsInfoRespDto;
import com.moirrra.novel.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.moirrra.novel.core.constant.ApiRouterConsts;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-23
 * @Description: 前台门户-新闻模块API控制器
 * @Version: 1.0
 */
@Tag(name = "NewsController", description = "前台门户-新闻模块")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_NEWS_URL_PREFIX)
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "最新新闻列表查询接口")
    @GetMapping("latest_list")
    public RestResp<List<NewsInfoRespDto>> listLatestNews() {
        return newsService.listLatestNews();
    }

    @Operation(summary = "新闻信息查询接口")
    @GetMapping("{id}")
    public RestResp<NewsInfoRespDto> getNews(@Parameter(description = "新闻ID") @PathVariable Long id) {
        return newsService.getNews(id);
    }

}
