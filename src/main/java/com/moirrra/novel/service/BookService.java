package com.moirrra.novel.service;

import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.dao.entity.BookInfo;
import com.moirrra.novel.dto.req.UserCommentReqDto;
import com.moirrra.novel.dto.resp.BookChapterAboutRespDto;
import com.moirrra.novel.dto.resp.BookCommentRespDto;
import com.moirrra.novel.dto.resp.BookInfoRespDto;

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
}
