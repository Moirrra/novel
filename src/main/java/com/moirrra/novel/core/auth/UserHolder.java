package com.moirrra.novel.core.auth;

import lombok.experimental.UtilityClass;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-22
 * @Description: 用户信息持有类
 * @Version: 1.0
 */
@UtilityClass
public class UserHolder {

    /**
     * 当前线程 用户ID
     */
    private static final ThreadLocal<Long> userIdTL = new ThreadLocal<>();

    public Long getUserId() {
        return userIdTL.get();
    }

    public void setUserId(Long userId) {
        userIdTL.set(userId);
    }

    public void clear() {
        userIdTL.remove();
    }
}
