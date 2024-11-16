package com.moirrra.novel.core.common.resp;

import lombok.Getter;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-10-26
 * @Description: 分页响应数据格式封装
 * @Version: 1.0
 */
@Getter
public class PageRespDto<T> {
    /**
     * 页码
     */
    private final long pageNum;

    /**
     * 每页大小
     */
    private final long pageSize;

    /**
     * 总记录数
     */
    private final long total;

    /**
     * 分页数据集
     * T类型或者其子类型
     */
    private final List<? extends T> list;

    public PageRespDto(long pageNum, long pageSize, long total, List<? extends T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public static <T> PageRespDto<T> of(long pageNum, long pageSize, long total, List<T> list) {
        return new PageRespDto<>(pageNum, pageSize, total, list);
    }

    /**
     * @return 分页数
     */
    public long getPages() {
        if (this.pageNum == 0L) {
            return 0L;
        } else { // 向上取整
            long pages = this.total / this.pageSize;
            if (this.total % this.pageSize != 0L) {
                pages++;
            }
            return pages;
        }
    }
}
