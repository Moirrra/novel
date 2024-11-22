package com.moirrra.novel.controller.front;

import com.moirrra.novel.core.auth.UserHolder;
import com.moirrra.novel.core.common.resp.RestResp;
import com.moirrra.novel.core.constant.ApiRouterConsts;
import com.moirrra.novel.dto.req.UserCommentReqDto;
import com.moirrra.novel.dto.req.UserLoginReqDto;
import com.moirrra.novel.dto.req.UserRegisterReqDto;
import com.moirrra.novel.dto.resp.UserLoginRespDto;
import com.moirrra.novel.dto.resp.UserRegisterRespDto;
import com.moirrra.novel.service.BookService;
import com.moirrra.novel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: 用户模块API控制器
 * @Version: 1.0
 */
@Tag(name = "UserController", description = "前台门户-用户模块")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_USER_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final BookService bookService;

    @Operation(summary = "用户注册接口")
    @PostMapping("register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto userRegisterReqDto) {
        return userService.register(userRegisterReqDto);
    }

    @Operation(summary = "用户登录接口")
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto userLoginReqDto) {
        return userService.login(userLoginReqDto);
    }

    @Operation(summary = "发表评论接口")
    @PostMapping("comment")
    public RestResp<Void> comment(@Valid @RequestBody UserCommentReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return bookService.saveComment(dto);
    }

    @Operation(summary = "修改评论接口")
    @PutMapping("comment/{id}")
    public RestResp<Void> updateComment(@Parameter(description = "评论ID") @PathVariable Long id,
                                        String content) {
        return bookService.updateComment(UserHolder.getUserId(), id, content);
    }

    @Operation(summary = "删除评论接口")
    @DeleteMapping("comment/{id}")
    public RestResp<Void> deleteComment(@Parameter(description = "评论ID") @PathVariable Long id) {
        return bookService.deleteComment(UserHolder.getUserId(), id);
    }
}
