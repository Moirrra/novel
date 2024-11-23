package com.moirrra.novel.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-23
 * @Description: 小说分类响应DTO
 * @Version: 1.0
 */
@Data
@Builder
public class BookCategoryRespDto {

    /**
     * 类别ID
     */
    @Schema(description = "类别ID")
    private Long id;

    /**
     * 类别名
     */
    @Schema(description = "类别名")
    private String name;
}
