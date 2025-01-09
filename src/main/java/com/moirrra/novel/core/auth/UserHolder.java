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

    /**
     * 当前线程作家ID
     */
    private static final ThreadLocal<Long> authorIdTL = new ThreadLocal<>();

    public Long getUserId() {
        return userIdTL.get();
    }

    public Long getAuthorId() {
        return authorIdTL.get();
    }

    public void setUserId(Long userId) {
        userIdTL.set(userId);
    }

    public void setAuthorId(Long authorId) {
        authorIdTL.set(authorId);
    }

    public void clear() {
        userIdTL.remove();
        authorIdTL.remove();
    }


}
