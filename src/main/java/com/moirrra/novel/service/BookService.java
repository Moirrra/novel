package com.moirrra.novel.service;

import com.moirrra.novel.core.common.req.PageReqDto;
import com.moirrra.novel.core.common.resp.PageRespDto;
import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dao.entity.BookInfo;
import com.moirrra.novel.dto.req.UserCommentReqDto;
import com.moirrra.novel.dto.resp.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 小说模块 服务接口
 * @Version: 1.0
 */

public interface BookService {

    /**
     * 小说信息查询
     * @param bookId 小说ID
     * @return 小说信息
     */
    RestResp<BookInfoRespDto> getBookById(Long bookId);

    /**
     * 推荐小说查询
     * @param bookId 小说ID
     * @return 小说信息列表
     */
    RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException;

    /**
     * 获取小说最新章节相关信息
     * @param bookId 小说ID
     * @return 说最新章节相关信息
     */
    RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId);

    /**
     * 增加小说点击量
     * @param bookId 小说ID
     * @return void
     */
    RestResp<Void> addVisitCount(Long bookId);

    /**
     * 查询小说最新评论
     * @param bookId 小说ID
     * @return 小说最新评论
     */
    RestResp<BookCommentRespDto> listNewestComment(Long bookId);

    /**
     * 发表评论
     * @param dto 用户评论DTO
     * @return void
     */
    RestResp<Void> saveComment(UserCommentReqDto dto);

    /**
     * 修改评论
     * @param userId 用户ID
     * @param id 评论ID
     * @param content 评论内容
     * @return void
     */
    RestResp<Void> updateComment(Long userId, Long id, String content);

    /**
     * 删除评论
     * @param userId 用户ID
     * @param id 评论ID
     * @return void
     */
    RestResp<Void> deleteComment(Long userId, Long id);

    /**
     * 获取小说章节列表
     * @param bookId 小说ID
     * @return 小说章节列表
     */
    RestResp<List<BookChapterRespDto>> listChapters(Long bookId);

    /**
     * 查询小说内容相关信息
     * @param chapterId 章节ID
     * @return 小说内容相关信息
     */
    RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId);

    /**
     * 获取上一章节ID
     * @param chapterId 章节ID
     * @return 上一章节ID
     */
    RestResp<Long> getPreChapterId(Long chapterId);

    /**
     * 获取下一章节ID
     * @param chapterId 章节ID
     * @return 下一章节ID
     */
    RestResp<Long> getNextChapterId(Long chapterId);

    /**
     * 查询小说点击榜
     * @return 小说点击榜列表
     */
    RestResp<List<BookRankRespDto>> listVisitRankBooks();

    /**
     * 查询小说新书榜
     * @return 小说新书榜列表
     */
    RestResp<List<BookRankRespDto>> listNewestRankBooks();

    /**
     * 查询小说更新榜
     * @return 小说更新榜列表
     */
    RestResp<List<BookRankRespDto>> listUpdateRankBooks();

    /**
     * 获取小说分类
     * @param workDirection 作品频道
     * @return 小说分类列表
     */
    RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection);

    /**
     * 分页查询用户评论
     * @param userId 用户ID
     * @param pageReqDto 分页查询请求DTO
     * @return 用户评论分页查询响应DTO
     */
    RestResp<PageRespDto<UserCommentRespDto>> listComments(Long userId, PageReqDto pageReqDto);
}
