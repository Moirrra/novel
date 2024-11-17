package com.moirrra.novel.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moirrra.novel.core.constant.CacheConsts;
import com.moirrra.novel.dao.entity.BookInfo;
import com.moirrra.novel.dao.entity.HomeBook;
import com.moirrra.novel.dao.mapper.BookInfoMapper;
import com.moirrra.novel.dao.mapper.HomeBookMapper;
import com.moirrra.novel.dto.resp.HomeBookRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-16
 * @Description: 首页推荐小说 缓存管理类
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class HomeBookCacheManager {

    private final HomeBookMapper homeBookMapper;

    private final BookInfoMapper bookInfoMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.HOME_BOOK_CACHE_NAME)
    public List<HomeBookRespDto> listHomeBooks() {
        // 查询推荐小说表
        QueryWrapper<HomeBook> homeBookQueryWrapper = new QueryWrapper<HomeBook>()
            .orderByAsc("sort"); // 按sort升序
        List<HomeBook> homeBooks = homeBookMapper.selectList(homeBookQueryWrapper);

        // 获取小说ID列表
        if (!CollectionUtils.isEmpty(homeBooks)) {
            List<Long> bookIds = homeBooks.stream()
                    .map(HomeBook::getBookId)
                    .toList();

            // 根据小说ID列表查询相关小说信息
            QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<BookInfo>();
            bookInfoQueryWrapper.in("id", bookIds);
            List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);

            // 组装 HomeBookRespDto 列表数据并返回
            if (!CollectionUtils.isEmpty(bookInfos)) {
                Map<Long, BookInfo> bookInfoMap = bookInfos.stream()
                        .collect(Collectors.toMap(BookInfo::getId, Function.identity()));
                return homeBooks.stream().map(v -> {
                    BookInfo bookInfo = bookInfoMap.get(v.getBookId());
                    HomeBookRespDto bookRespDto = new HomeBookRespDto();
                    bookRespDto.setBookId(v.getBookId());
                    bookRespDto.setBookName(bookInfo.getBookName());
                    bookRespDto.setPicUrl(bookInfo.getPicUrl());
                    bookRespDto.setAuthorName(bookInfo.getAuthorName());
                    bookRespDto.setBookDesc(bookInfo.getBookDesc());
                    return bookRespDto;
                }).toList();
            }
        }

        return Collections.emptyList();
    }

}
