package com.moirrra.novel.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-24
 * @Description: 作者信息 DTO
 * @Version: 1.0
 */
@Data
@Builder
public class AuthorInfoDto {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String penName;

    private Integer status;
}
