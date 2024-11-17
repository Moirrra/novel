package com.moirrra.novel.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-17
 * @Description: 首页友情链接DTO
 * @Version: 1.0
 */
@Data
public class HomeFriendLinkRespDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 链接名
     */
    @Schema(description = "链接名")
    private String linkName;

    /**
     * 链接url
     */
    @Schema(description = "链接url")
    private String linkUrl;
}
