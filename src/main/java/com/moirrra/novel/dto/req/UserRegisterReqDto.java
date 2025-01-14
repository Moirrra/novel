package com.moirrra.novel.dto.req;

import com.moirrra.novel.core.common.constant.CommonConsts;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 用户注册请求DTO
 * @Version: 1.0
 */
@Data
public class UserRegisterReqDto {

    @Schema(description = "手机号", required = true)
    @NotBlank(message="手机号不能为空！")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$", message = "手机号格式不正确！")
    private String username;

    @Schema(description = "密码", required = true)
    @NotBlank(message="密码不能为空！")
    private String password;

    @Schema(description = "验证码", required = true)
    @NotBlank(message="验证码不能为空！")
    @Pattern(regexp="^\\d{4}$",message="验证码格式不正确！")
    private String velCode;

    @Schema(description = "sessionId", required = true)
    @NotBlank
    @Length(min = CommonConsts.SESSION_ID_LENGTH, max = CommonConsts.SESSION_ID_LENGTH)
    private String sessionId;
}
