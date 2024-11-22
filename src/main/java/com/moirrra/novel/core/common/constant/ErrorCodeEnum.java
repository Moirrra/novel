package com.moirrra.novel.core.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-10-26
 * @Description: 错误码枚举类
 * 错误产生来源(1) + 数字编号(4)
 * 错误产生来源 A/B/C
 *  A:来源于用户，比如参数错误，用户安装版本过低，用户支付
 *  B:来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题
 *  C:来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题
 * 数字编号 0001-9999，大类之间的步长间距预留 100
 * 错误码分为一级宏观错误码、二级宏观错误码、三级宏观错误码
 * 在无法更加具体确定的错误场景中，可以直接使用一级宏观错误码
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    /**
     * 正确执行
     */
    OK("00000","ok"),

    /**
     * 一级宏观错误码，用户端错误
     */
    USER_ERROR("A0001", "用户端错误"),

    /**
     * 二级宏观错误码，用户注册错误
     */
    USER_REGISTER_ERROR("A0100", "用户注册错误"),

    /**
     * 二级宏观错误码，用户未同意隐私协议
     */
    USER_NO_AGREE_PRIVATE_ERROR("A0101", "用户为同意隐私协议"),

    /**
     * 二级宏观错误码，注册国家或地区受限
     * */
    USER_REGISTER_AREA_LIMIT_ERROR("A0102","注册国家或地区受限"),

    /**
     * 用户验证码错误
     */
    USER_VERIFY_CODE_ERROR("A0103", "用户验证码错误"),

    /**
     * 用户名已存在
     */
    USER_NAME_EXIST("A0104", "用户名已存在"),

    /**
     * 用户账号不存在
     */
    USER_ACCOUNT_NOT_EXIST("A0201", "用户账号不存在"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_ERROR("A0210", "用户密码错误"),

    /**
     * 二级宏观错误码，用户请求参数错误
     * */
    USER_REQUEST_PARAM_ERROR("A0400","用户请求参数错误"),

    /**
     * 用户评论异常
     */
    USER_COMMENT("A2000", "用户评论异常"),

    /**
     * 用户发表评论失败
     */
    USER_COMMENT_INSERT_ERROR("A2001", "用户发表评论失败"),

    /**
     * 用户修改评论失败
     */
    USER_COMMENT_UPDATE_ERROR("A2001", "用户修改评论失败"),

    /**
     * 用户删除评论失败
     */
    USER_COMMENT_DELETE_ERROR("A2002", "用户删除评论失败"),

    // ...省略若干用户端二级宏观错误码

    /**
     * 一级宏观错误码，系统执行出错
     * */
    SYSTEM_ERROR("B0001","系统执行出错"),

    /**
     * 二级宏观错误码，系统执行超时
     * */
    SYSTEM_TIMEOUT_ERROR("B0100","系统执行超时"),

    // ...省略若干系统执行二级宏观错误码

    /**
     * 一级宏观错误码，调用第三方服务出错
     * */
    THIRD_SERVICE_ERROR("C0001","调用第三方服务出错"),

    /**
     * 一级宏观错误码，中间件服务出错
     * */
    MIDDLEWARE_SERVICE_ERROR("C0100","中间件服务出错")

    // ...省略若干三方服务调用二级宏观错误码
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String message;

}
