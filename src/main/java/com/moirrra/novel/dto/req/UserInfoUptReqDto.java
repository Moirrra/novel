package com.moirrra.novel.dto.req;

import com.moirrra.novel.core.common.constant.CommonConsts;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-24
 * @Description: 用户信息修改 请求DTO
 * @Version: 1.0
 */
@Data
public class UserInfoUptReqDto {

    private Long userId;

    @Schema(description = "昵称")
    @Length(min = CommonConsts.NICKNAME_MIN_LENGTH, max = CommonConsts.NICKNAME_MAX_LENGTH)
    private String nickName;


    @Schema(description = "头像地址")
    @Pattern(regexp="[^\s]{2,90}(\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|bpm|BPM))$")
    private String userPhoto;

    @Schema(description = "性别")
    @Min(value = 0)
    @Max(value = 1)
    private Integer userSex;
}
