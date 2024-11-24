package com.moirrra.novel.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-24
 * @Description: 用户信息 响应DTO
 * @Version: 1.0
 */
@Data
@Builder
public class UserInfoRespDto {
    /**
     * 昵称
     * */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 用户头像
     * */
    @Schema(description = "用户头像")
    private String userPhoto;

    /**
     * 用户性别
     * */
    @Schema(description = "用户性别")
    private Integer userSex;
}
