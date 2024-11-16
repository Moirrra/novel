package com.moirrra.novel.controller.front;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.core.constant.ApiRouterConsts;
import com.moirrra.novel.dto.resp.HomeBookRespDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-16
 * @Description: 首页模块 API 接口
 * @Version: 1.0
 */
@RestController(ApiRouterConsts.HOME_URL_PREFIX)
public class HomeController {
    @Operation(summary = "首页小说推荐查询接口")
    @GetMapping("books")
    public RestResp<List<HomeBookRespDto>> listHomeBooks() {

        return null;
    }
}
