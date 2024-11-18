package com.moirrra.novel.core.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description:
 * @Version: 1.0
 */

public class CommunityUtils {
    /**
     * 随机生成字符串
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * MD5加密
     * @param salt
     * @return
     */
    public static String md5(String salt) {
        if (StringUtils.isBlank(salt)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(salt.getBytes());
    }
}
