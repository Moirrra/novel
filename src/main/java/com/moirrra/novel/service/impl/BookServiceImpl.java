package com.moirrra.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moirrra.novel.core.common.constant.ErrorCodeEnum;
import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.core.constant.DatabaseConsts;
import com.moirrra.novel.dao.entity.BookChapter;
import com.moirrra.novel.dao.entity.BookComment;
import com.moirrra.novel.dao.entity.BookInfo;
import com.moirrra.novel.dao.entity.UserInfo;
import com.moirrra.novel.dao.mapper.BookChapterMapper;
import com.moirrra.novel.dao.mapper.BookCommentMapper;
import com.moirrra.novel.dao.mapper.BookInfoMapper;
import com.moirrra.novel.dao.mapper.UserInfoMapper;
import com.moirrra.novel.dto.req.UserCommentReqDto;
import com.moirrra.novel.dto.resp.*;
import com.moirrra.novel.manager.cache.*;
import com.moirrra.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 小说模块 服务实现类
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookChapterCacheManager bookChapterCacheManager;

    private final BookContentCacheManager bookContentCacheManager;

    private final BookCategoryCacheManager bookCategoryCacheManager;

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

    private final UserInfoMapper userInfoMapper;

    private static final Integer REC_BOOK_COUNT = 4;

    private static final Integer CHAPTER_ABOUT_START = 0;

    private static final Integer CHAPTER_ABOUT_END = 100;
    private final BookCommentMapper bookCommentMapper;
    private final BookRankCacheManager bookRankCacheManager;

    @Override
    public RestResp<BookInfoRespDto> getBookById(Long bookId) {
        return RestResp.ok(bookInfoCacheManager.getBookInfo(bookId));
    }

    /**
     * 随机生成同类小说推荐列表
     * @param bookId 小说ID
     * @return 推荐小说信息列表
     * @throws NoSuchAlgorithmException
     */
    @Override
    public RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException {
        Long categoryId = bookInfoCacheManager.getBookInfo(bookId).getCategoryId();
        List<Long> lastUpdateIdList = bookInfoCacheManager.getLastUpdateIdList(categoryId); // 最近更新的同类小说列表
        List<BookInfoRespDto> respDtoList = new ArrayList<>(); // 推荐小说的信息列表
        List<Integer> recIdIndexList = new ArrayList<>(); // 推荐小说的下标列表
        // 随机获取指定数量的推荐小说
        Random rand = SecureRandom.getInstanceStrong();
        int count = 0;
        while (count < REC_BOOK_COUNT) {
            int recIdIndex = rand.nextInt(lastUpdateIdList.size());
            if (!recIdIndexList.contains(recIdIndex)) {
                recIdIndexList.add(recIdIndex);
                bookId = lastUpdateIdList.get(recIdIndex);
                BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookId);
                respDtoList.add(bookInfo);
                count++;
            }
        }
        return RestResp.ok(respDtoList);
    }

    @Override
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId) {
        // 查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookId);

        // 获取最新章节信息
        BookChapterRespDto bookChapterRespDto = bookChapterCacheManager.getChapter(bookInfo.getLastChapterId());

        // 查询最新章节内容
        String content = bookContentCacheManager.getBookContent(bookInfo.getLastChapterId());

        // 查询章节总数
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId);
        Long chapterTotal = bookChapterMapper.selectCount(queryWrapper);

        // 组装数据并返回
        return RestResp.ok(BookChapterAboutRespDto.builder()
                .chapterInfo(bookChapterRespDto)
                .chapterTotal(chapterTotal)
                .contentSummary(content.substring(CHAPTER_ABOUT_START, CHAPTER_ABOUT_END))
                .build());
    }

    @Override
    public RestResp<Void> addVisitCount(Long bookId) {
        bookInfoMapper.addVisitCount(bookId);
        return RestResp.ok();
    }

    @Override
    public RestResp<BookCommentRespDto> listNewestComment(Long bookId) {
        // 查询&设置评论总数
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookCommentTable.COLUMN_BOOK_ID, bookId);
        Long commentTotal = bookCommentMapper.selectCount(queryWrapper);
        BookCommentRespDto bookCommentRespDto = BookCommentRespDto.builder()
                .commentTotal(commentTotal)
                .build();

        if (commentTotal > 0) {
            // 查询最新评论列表
            QueryWrapper<BookComment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq(DatabaseConsts.BookCommentTable.COLUMN_BOOK_ID, bookId)
                    .orderByDesc(DatabaseConsts.CommonColumnEnum.CREATE_TIME.getName())
                    .last(DatabaseConsts.SqlEnum.LIMIT_5.getSql());
            List<BookComment> bookComments = bookCommentMapper.selectList(commentQueryWrapper);

            // 查询评论用户信息
            List<Long> userIds = bookComments.stream().map(BookComment::getUserId).toList();
            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
            userInfoQueryWrapper.in(DatabaseConsts.CommonColumnEnum.ID.getName(), userIds);
            List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
            Map<Long, UserInfo> userInfoMap = userInfos.stream()
                    .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
            // 组装评论列表
            List<BookCommentRespDto.CommentInfo> commentInfos = bookComments.stream()
                    .map(v -> BookCommentRespDto.CommentInfo.builder()
                            .id(v.getId())
                            .commentUserId(v.getUserId())
                            .commentUser(userInfoMap.get(v.getUserId()).getUsername())
                            .commentUserPhoto(userInfoMap.get(v.getUserId()).getUserPhoto())
                            .commentContent(v.getCommentContent())
                            .commentTime(v.getCreateTime())
                            .build()
                    ).toList();
            bookCommentRespDto.setComments(commentInfos);
        } else {
            bookCommentRespDto.setComments(Collections.emptyList());
        }

        return RestResp.ok(bookCommentRespDto);
    }

    @Override
    public RestResp<Void> saveComment(UserCommentReqDto dto) {
        BookComment bookComment = new BookComment();
        bookComment.setBookId(dto.getBookId());
        bookComment.setUserId(dto.getUserId());
        bookComment.setCommentContent(dto.getCommentContent());
        bookComment.setCreateTime(LocalDateTime.now());
        bookComment.setUpdateTime(LocalDateTime.now());
        int count = bookCommentMapper.insert(bookComment);
        if (count > 0) {
            return RestResp.ok();
        } else {
            return RestResp.fail(ErrorCodeEnum.USER_COMMENT_INSERT_ERROR);
        }
    }

    @Override
    public RestResp<Void> updateComment(Long userId, Long id, String content) {
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.CommonColumnEnum.ID.getName(), id)
                .eq(DatabaseConsts.BookCommentTable.COLUMN_USER_ID, userId);
        BookComment bookComment = new BookComment();
        bookComment.setCommentContent(content);
        int count = bookCommentMapper.update(bookComment, queryWrapper);
        if (count > 0) {
            return RestResp.ok();
        } else {
            return RestResp.fail(ErrorCodeEnum.USER_COMMENT_UPDATE_ERROR);
        }
    }

    @Override
    public RestResp<Void> deleteComment(Long userId, Long id) {
    QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(DatabaseConsts.CommonColumnEnum.ID.getName(), id)
            .eq(DatabaseConsts.BookCommentTable.COLUMN_USER_ID, userId);
        int count = bookCommentMapper.delete(queryWrapper);
        if (count > 0) {
            return RestResp.ok();
        } else {
            return RestResp.fail(ErrorCodeEnum.USER_COMMENT_DELETE_ERROR);
        }
    }


    @Override
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId)
                .orderByAsc(DatabaseConsts.BookChapterTable.COLUMN_CHAPTER_NUM);
        List<BookChapterRespDto> list = bookChapterMapper.selectList(queryWrapper).stream()
                .map(v -> BookChapterRespDto.builder()
                        .id(v.getId())
                        .chapterNum(v.getChapterNum())
                        .chapterName(v.getChapterName())
                        .bookId(v.getBookId())
                        .chapterWordCount(v.getWordCount())
                        .chapterUpdateTime(v.getUpdateTime())
                        .isVip(v.getIsVip())
                        .build()).toList();
        return RestResp.ok(list);
    }

    @Override
    public RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId) {
        // 查询章节信息
        BookChapterRespDto bookChapterRespDto = bookChapterCacheManager.getChapter(chapterId);

        // 查询小说信息
        BookInfoRespDto bookInfoRespDto = bookInfoCacheManager.getBookInfo(bookChapterRespDto.getBookId());

        // 查询章节内容
        String bookContent = bookContentCacheManager.getBookContent(chapterId);

        return RestResp.ok(BookContentAboutRespDto.builder()
                        .bookInfo(bookInfoRespDto)
                        .chapterInfo(bookChapterRespDto)
                        .bookContent(bookContent)
                        .build());
    }

    @Override
    public RestResp<Long> getPreChapterId(Long chapterId) {
        // 查询本章节信息
        BookChapterRespDto bookChapterRespDto = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = bookChapterRespDto.getBookId();
        Integer chapterNum = bookChapterRespDto.getChapterNum();

        // 查询上一章节信息
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId)
                .lt(DatabaseConsts.BookChapterTable.COLUMN_CHAPTER_NUM, chapterNum)
                .orderByDesc(DatabaseConsts.BookChapterTable.COLUMN_CHAPTER_NUM)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookChapter bookChapter = bookChapterMapper.selectOne(queryWrapper);
        if (bookChapter != null) {
            return RestResp.ok(bookChapter.getId());
        }
        return RestResp.ok(null);
    }

    @Override
    public RestResp<Long> getNextChapterId(Long chapterId) {
        // 查询本章节信息
        BookChapterRespDto bookChapterRespDto = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = bookChapterRespDto.getBookId();
        Integer chapterNum = bookChapterRespDto.getChapterNum();

        // 查询下一章节信息
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookChapterTable.COLUMN_BOOK_ID, bookId)
                .gt(DatabaseConsts.BookChapterTable.COLUMN_CHAPTER_NUM, chapterNum)
                .orderByAsc(DatabaseConsts.BookChapterTable.COLUMN_CHAPTER_NUM)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookChapter bookChapter = bookChapterMapper.selectOne(queryWrapper);
        if (bookChapter != null) {
            return RestResp.ok(bookChapter.getId());
        }
        return RestResp.ok(null);
    }

    @Override
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return RestResp.ok(bookRankCacheManager.listVisitRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listNewestRankBooks() {
        return RestResp.ok(bookRankCacheManager.listNewestRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return RestResp.ok(bookRankCacheManager.listUpdateRankBooks());
    }

    @Override
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return RestResp.ok(bookCategoryCacheManager.listCategory(workDirection));
    }

}
