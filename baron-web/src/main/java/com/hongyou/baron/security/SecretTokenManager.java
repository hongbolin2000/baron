///*
// * Copyright 2024, Hongyou Software Development Studio.
// */
//package com.hongyou.baron.security;
//
//import com.hongyou.baron.logging.Log;
//import com.hongyou.baron.logging.LogFactory;
//import com.hongyou.baron.util.DateUtil;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.UUID;
//
///**
// * JWT Token管理器
// *
// * @author Berlin
// */
//@Component
//public class SecretTokenManager {
//
//    /**
//     * logger
//     */
//    private static final Log logger = LogFactory.getLog(SecretTokenManager.class);
//
//    /**
//     * 令牌在HTTP Header中表示的标识
//     */
//    @Value("${hongyou.token.header:Authentication}")
//    private String tokenHeader;
//
//    /**
//     * Token过期时间，缺省为30分钟
//     */
//    @Value("${hongyou.token.expire-minutes:30}")
//    private int expireMinutes;
//
//    /**
//     * 生成Token的密匙
//     */
//    @Value("${hongyou.token.secret:6bf2511c67bf89e3f1574be08fa850164282b8d008bae1922eb1f51167d573b8}")
//    private String secret;
//
//    /**
//     * 存储在Token中用户信息的标识名称
//     */
//    @Value("${hongyou.token.user-title:user}")
//    private String tokenUserTitle;
//
//    /**
//     * Token生成前缀
//     */
//    private static final String TOKEN_PREFIX = "Hongyou ";
//
//    /**
//     * Token签发主题
//     */
//    private static final String SUBJECT = "Authentication";
//
//    /**
//     * 生成Token
//     *
//     * @param user 认证用户信息
//     */
//    public String generate(final AuthorizeUser user) {
//        SecretKey secretKey = Keys.hmacShaKeyFor(this.secret.getBytes());
//        String token = Jwts.builder().
//                header().add("typ", "JWT").add("alg", "HS256").and().
//                claim(this.tokenUserTitle, user).
//                id(this.getId()).
//                expiration(this.getExpireDate()).
//                issuedAt(new Date()).
//                issuer("Hongyou").
//                subject(SUBJECT).
//                signWith(secretKey, Jwts.SIG.HS256).
//                compact();
//        return TOKEN_PREFIX + token;
//    }
//
//    /**
//     * 解析Token
//     *
//     * @param token 令牌
//     * @return 解析通过后的JWT数据
//     */
//    private Jws<Claims> analyzeToken(final String token) {
//        Jws<Claims> jws = null;
//        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
//            logger.error("无法解析的Token: {0}" + token);
//            return jws;
//        }
//        try {
//            String tokenKey = token.substring(TOKEN_PREFIX.length());
//            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
//            jws = Jwts.parser().
//                    verifyWith(secretKey).
//                    build().
//                    parseSignedClaims(tokenKey);
//        } catch (ExpiredJwtException e) {
//            // token过期
//            return null;
//        } catch (Exception e) {
//            logger.error("Token解析失败: {0}", e, token);
//            return jws;
//        }
//        return jws;
//    }
//
//    /**
//     * 获取Token中携带的数据
//     *
//     * @param token 令牌
//     */
//    @SuppressWarnings("unchecked")
//    public AuthorizeUserDetail getUser(final String token) {
//        Jws<Claims> jws = this.analyzeToken(token);
//        if (jws != null) {
//            Claims claims = jws.getPayload();
//            LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) claims.get(this.tokenUserTitle);
//
//            AuthorizeUser authorizeUser = AuthorizeUser.builder().
//                    username((String) user.get("username")).
//                    password((String) user.get("password")).
//                    accountExpireTime(null).
//                    accountUnlock((boolean) user.get("accountUnlock")).
//                    credentialsExpireTime(null).
//                    enabled((boolean) user.get("accountUnlock")).
//                    build();
//            return new AuthorizeUserDetail(authorizeUser);
//        }
//        return null;
//    }
//
//    /**
//     * 将Token加入到HTTP请求头中
//     */
//    public void build(final String token, final HttpServletResponse response) {
//        response.addHeader(this.tokenHeader, token);
//    }
//
//    /**
//     * 从HTTP请求中获取Token
//     */
//    public String get(final HttpServletRequest request) {
//        return request.getHeader(this.tokenHeader);
//    }
//
//    /**
//     * 判断Token是否失效
//     *
//     * @param claims 令牌解密后携带的数据
//     * @return 返回true则令牌失效
//     */
//    public boolean expire(final Claims claims) {
//        return claims == null || claims.getExpiration().before(new Date());
//    }
//
//    /**
//     * 计算Token过期时间
//     */
//    private Date getExpireDate() {
//        long timestamp = DateUtil.getTimestamp();
//        return new Date(timestamp + ((long) this.expireMinutes * 60 * 1000));
//    }
//
//    /**
//     * 生成Token ID
//     */
//    private String getId() {
//        return UUID.randomUUID().toString().replace("-","");
//    }
//}
