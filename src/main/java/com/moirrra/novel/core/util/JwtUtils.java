package com.moirrra.novel.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-18
 * @Description: JWT 工具类
 * @Version: 1.0
 */

@Component
@ConditionalOnProperty("novel.jwt.secret")
@Slf4j
public class JwtUtils {
    @Value("${novel.jwt.secret}")
    private String secret;

    /**
     * 系统标示头常量
     */
    private static final String HEADER_SYSTEM_KEY =  "systemKeyHeader";

    /**
     * 根据用户ID生成JWT
     * @param uid 用户id
     * @param systemKey 系统表示
     * @return JWT
     */
    public String generateToken(Long uid, String systemKey) {
        return Jwts.builder()
                .setHeaderParam(HEADER_SYSTEM_KEY, systemKey)
                .setSubject(uid.toString()) // 设置令牌主题，作为用户的唯一标志
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))) // 设置签名算法&签名使用的秘钥
                .compact(); // 转换为字符串
    }

    /**
     * 解析 JWT 返回用户ID
     * @param token JWT
     * @param systemKey 系统标识
     * @return 用户ID
     */
    public Long parseToken(String token, String systemKey) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            // 根据header里的属性判断该JWT是否属于指定系统
            if (Objects.equals(claimsJws.getHeader().get(HEADER_SYSTEM_KEY), systemKey)) {
                return Long.parseLong(claimsJws.getBody().getSubject());
            }
        } catch (JwtException e) {
            log.warn("JWT解析失败:{}", token);
        }
        return null;
    }

}
