package com.moirrra.novel.manager.cache;

import com.moirrra.novel.core.constant.CacheConsts;
import com.moirrra.novel.dao.entity.BookChapter;
import com.moirrra.novel.dao.mapper.BookChapterMapper;
import com.moirrra.novel.dto.resp.BookChapterRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-20
 * @Description: 小说章节信息缓存管理类
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class BookChapterCacheManager {

    private final BookChapterMapper bookChapterMapper;

    /**
     * 查询小说章节信息，并放入缓存
     * @param chapterId 章节ID
     * @return 小说章节信息响应DTO
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_CHAPTER_CACHE_NAME)
    public BookChapterRespDto getChapter(Long chapterId) {
        BookChapter bookChapter = bookChapterMapper.selectById(chapterId);
        return BookChapterRespDto.builder()
                .id(bookChapter.getId())
                .bookId(bookChapter.getBookId())
                .chapterNum(bookChapter.getChapterNum())
                .chapterName(bookChapter.getChapterName())
                .chapterWordCount(bookChapter.getWordCount())
                .chapterUpdateTime(bookChapter.getUpdateTime())
                .isVip(bookChapter.getIsVip())
                .build();
    }

    /**
     * 自动清除小说章节信息的缓存
     * @param chapterId 章节ID
     */
    @CacheEvict(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER, value = CacheConsts.BOOK_CHAPTER_CACHE_NAME)
    public void evictBookChapterCache(Long chapterId) {}
}
