package com.manastudent.core.util;

public final class SecurityConstants {

    /**
     * 角色的key
     */
    public static final String ROLE_CLAIMS = "role";

    /**
     * rememberMe 为 false 的时候过期时间是1个小时
     */
    public static final long EXPIRATION = 60 * 60L;

    /**
     * rememberMe 为 true 的时候过期时间是7天
     */
    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /**
     * JWT签名密钥
     */
    public static final String JWT_SECRET_KEY = "sdfasdfsadfsa()dfsa3234kjnhccru(*&^%%%$$jddd";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    // System WHITELIST
    public static final String[] SYSTEM_WHITELIST = {
            "/login"
    };
}
