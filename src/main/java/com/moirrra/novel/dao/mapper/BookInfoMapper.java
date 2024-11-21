package com.moirrra.novel.dao.mapper;

import com.moirrra.novel.dao.entity.BookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 小说信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @date 2024/11/16
 */
public interface BookInfoMapper extends BaseMapper<BookInfo> {
    /**
     * 增加小说点击量
     * @param bookId 小说ID
     */
    void addVisitCount(@Param("bookId") Long bookId);
}
