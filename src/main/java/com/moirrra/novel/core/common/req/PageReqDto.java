package com.moirrra.novel.core.common.req;

import lombok.Data;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-10-26
 * @Description: 分页请求数据格式DTO, 所有分页请求的DTO类都应继承该类
 * @Version: 1.0
 */
@Data
public class PageReqDto {
    /**
     * 请求页码 默认第 1 页
     */
    private int pageNum = 1;
    /**
     * 每页大小 默认每页 10 条
     */
    private int pageSize = 10;

    /**
     * 是否查询所有 默认否
     * true: pageNum 和 pageSize 不生效
     */
    private boolean fetchAll = false;
}
