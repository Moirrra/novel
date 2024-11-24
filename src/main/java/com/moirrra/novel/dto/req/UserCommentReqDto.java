package com.moirrra.novel.dto.req;

import com.moirrra.novel.core.common.constant.CommonConsts;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-22
 * @Description: 用户评论请求DTO
 * @Version: 1.0
 */
@Data
public class UserCommentReqDto {
    private Long userId;

    @Schema(description = "小说ID", required = true)
    @NotNull(message="小说ID不能为空！")
    private Long bookId;

    @Schema(description = "评论内容", required = true)
    @NotBlank(message="评论不能为空！")
    @Length(min = CommonConsts.COMMENT_MIN_LENGTH, max = CommonConsts.COMMENT_MAX_LENGTH)
    private String commentContent;
}
