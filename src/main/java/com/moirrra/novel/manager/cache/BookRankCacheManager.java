package com.moirrra.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moirrra.novel.core.constant.CacheConsts;
import com.moirrra.novel.core.constant.DatabaseConsts;
import com.moirrra.novel.dao.entity.BookInfo;
import com.moirrra.novel.dao.mapper.BookInfoMapper;
import com.moirrra.novel.dto.resp.BookRankRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-23
 * @Description: 小说排行榜 缓存管理类
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class BookRankCacheManager {

    private final BookInfoMapper bookInfoMapper;

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
        value = CacheConsts.BOOK_VISIT_RANK_CACHE_NAME)
    public List<BookRankRespDto> listVisitRankBooks() {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(DatabaseConsts.BookTable.COLUMN_VISIT_COUNT);
        return listRankBooks(queryWrapper);
    }

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_NEWEST_RANK_CACHE_NAME)
    public List<BookRankRespDto> listNewestRankBooks() {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(DatabaseConsts.CommonColumnEnum.CREATE_TIME.getName());
        return listRankBooks(queryWrapper);
    }

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_UPDATE_RANK_CACHE_NAME)
    public List<BookRankRespDto> listUpdateRankBooks() {
        QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(DatabaseConsts.CommonColumnEnum.UPDATE_TIME.getName());
        return listRankBooks(queryWrapper);
    }


    private List<BookRankRespDto> listRankBooks(QueryWrapper<BookInfo> queryWrapper) {
        queryWrapper.gt(DatabaseConsts.BookTable.COLUMN_WORD_COUNT, 0)
                .last(DatabaseConsts.SqlEnum.LIMIT_30.getSql());
        return bookInfoMapper.selectList(queryWrapper).stream()
                .map(v -> {
                    BookRankRespDto bookRankRespDto = new BookRankRespDto();
                    bookRankRespDto.setId(v.getId());
                    bookRankRespDto.setCategoryId(v.getCategoryId());
                    bookRankRespDto.setCategoryName(v.getCategoryName());
                    bookRankRespDto.setBookName(v.getBookName());
                    bookRankRespDto.setAuthorName(v.getAuthorName());
                    bookRankRespDto.setPicUrl(v.getPicUrl());
                    bookRankRespDto.setBookDesc(v.getBookDesc());
                    bookRankRespDto.setLastChapterName(v.getLastChapterName());
                    bookRankRespDto.setLastChapterUpdateTime(v.getLastChapterUpdateTime());
                    bookRankRespDto.setWordCount(v.getWordCount());
                    return bookRankRespDto;
                }).toList();
    }
}
