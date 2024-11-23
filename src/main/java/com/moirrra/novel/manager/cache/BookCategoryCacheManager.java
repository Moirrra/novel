package com.moirrra.novel.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moirrra.novel.core.constant.CacheConsts;
import com.moirrra.novel.core.constant.DatabaseConsts;
import com.moirrra.novel.dao.entity.BookCategory;
import com.moirrra.novel.dao.mapper.BookCategoryMapper;
import com.moirrra.novel.dto.resp.BookCategoryRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-23
 * @Description: 小说分类缓存管理类
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class BookCategoryCacheManager {

    private final BookCategoryMapper bookCategoryMapper;

    /**
     * 根据作品频道查询小说分类列表，并放入缓存
     * @param workDirection 作品频道
     * @return 小说分类列表
     */
    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.BOOK_CATEGORY_LIST_CACHE_NAME)
    public List<BookCategoryRespDto> listCategory(Integer workDirection) {
        QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.BookCategoryTable.COLUMN_WORK_DIRECTION, workDirection);
        return bookCategoryMapper.selectList(queryWrapper).stream()
                .map(v -> BookCategoryRespDto.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .build()).toList();
    }
}
