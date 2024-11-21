package com.moirrra.novel.controller.front;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.core.constant.ApiRouterConsts;
import com.moirrra.novel.dto.resp.BookChapterAboutRespDto;
import com.moirrra.novel.dto.resp.BookCommentRespDto;
import com.moirrra.novel.dto.resp.BookInfoRespDto;
import com.moirrra.novel.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 前台门户-小说模块 API 控制器
 * @Version: 1.0
 */
@Tag(name = "BookController", description = "前台门户-小说模块")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_BOOK_URL_PREFIX)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "小说信息查询接口")
    @GetMapping("{id}")
    public RestResp<BookInfoRespDto> getBookById(
            @Parameter(description="小说ID") @PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }


    @Operation(summary = "小说推荐列表查询接口")
    @GetMapping("rec_list")
    public RestResp<List<BookInfoRespDto>> listRecBooks(
            @Parameter(description = "小说ID") Long bookId) throws NoSuchAlgorithmException {
        return bookService.listRecBooks(bookId);
    }

    @Operation(summary = "小说最新章节相关信息查询接口")
    @GetMapping("last_chapter/about")
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(@Parameter(description = "小说ID") Long bookId) {
        return bookService.getLastChapterAbout(bookId);
    }

    @Operation(summary = "增加小说点击量接口")
    @PostMapping("visit")
    public RestResp<Void> addVisitCount(@Parameter(description = "小说ID") @RequestBody Map<String, Object> map) {
        Long bookId = Long.valueOf(map.get("bookId").toString());
        return bookService.addVisitCount(bookId);
    }

    @Operation(summary = "小说最新评论查询接口")
    @GetMapping("comment/newest_list")
    public RestResp<BookCommentRespDto> listNewestComment(@Parameter(description = "小说ID") Long bookId) {
        return bookService.listNewestComment(bookId);
    }
}
