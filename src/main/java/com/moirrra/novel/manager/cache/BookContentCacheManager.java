package com.moirrra.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moirrra.novel.core.constant.CacheConsts;
import com.moirrra.novel.core.constant.DatabaseConsts;
import com.moirrra.novel.dao.entity.BookContent;
import com.moirrra.novel.dao.mapper.BookContentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-20
 * @Description: 小说内容缓存管理类
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class BookContentCacheManager {

    private final BookContentMapper bookContentMapper;

    /**
     * 查询小说内容缓存
     * @param chapterId 章节ID
     * @return 小说内容
     */
    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_CONTENT_CACHE_NAME)
    public String getBookContent(Long chapterId) {
        QueryWrapper<BookContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookContentTable.COLUMN_CHAPTER_ID, chapterId)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookContent bookContent = bookContentMapper.selectOne(queryWrapper);
        return bookContent.getContent();
    }

    /**
     * 自动清除小说内容信息的缓存
     * @param chapterId 章节ID
     */
    @CacheEvict(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_CONTENT_CACHE_NAME)
    public void evictBookContentCache(Long chapterId) {}
}
