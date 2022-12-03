package com.briup.crmcommon.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @Description: jwt工具类
 * @Author: GX Cui
 * @Date 上午11:45 2022/11/16
 */
@Component
public class JwtTokenUtils {

    /**
     * token在header里的属性名，默认为Authorization
     * 也可改为其他的
     */
    public static final String TOKEN_HEADER = "token";
    /**
     * 密钥key
     */
    private static final String SECRET = "jwtsecurit";

    /**
     * JWT的发行人
     */
    private static final String ISS = "Kunshan Briup";

    /**
     * 自定义用户信息
     */
    private static final String ROLE_CLAIMS = "cgx";

    /**
     * 过期时间是3600秒，既是1个小时
     */
    public static final long EXPIRATION = 3600L * 1000;

    /**
     * 选择了记住我之后的过期时间为7天
     */
    public static final long EXPIRATION_REMEMBER = 604800L * 1000;

    /**
     * 验证令牌
     */
    private static final String ACCESS_TOKEN = "access_token";
    /**
     * 刷新令牌
     * 用来在验证令牌失效的时候快速产生新的令牌，过期时间为验证令牌的两倍
     * 利用两个令牌之间的时间差完成令牌更新
     */
    private static final String REFRESH_TOKEN = "refresh_token";
    /**
     * cache密钥，用来在redis里做标识的
     */
    private static final String JWT_CACHE_KEY = "jwt:userId:";
    /**
     * 用户id标识
     */
    private static final String USER_ID = "userId";
    /**
     * 用户名标识
     */
    private static final String USER_NAME = "username";
    /**
     * 过期时间标识
     */
    private static final String EXPIRE_IN = "expire_in";


    /**
     * 因为这里调用的是redis的操作对象
     * 所以不太能使用静态使用方法，如果想使用静态方法，这部分内容可以自己放在Controller去写
     */
    @Autowired
    private static StringRedisTemplate stringRedisTemplate;


    /**
     * 根据用户id或者用户名生成token令牌
     * @param userId
     * @param username
     * @return
     */
    public Map<String, Object> generateTokenAndRefreshToken(String userId, String username) {
        Map<String, Object> tokenMap = buildToken(userId, username);
        cacheToken(userId, tokenMap);
        return tokenMap;
    }

    /**
     * 缓存token到redis中，以userId为标识
     * @param userId
     * @param tokenMap
     */
    private void cacheToken(String userId, @NotNull Map<String, Object> tokenMap) {
        // 保存认证token
        stringRedisTemplate.opsForHash().put(JWT_CACHE_KEY + userId, ACCESS_TOKEN, tokenMap.get(ACCESS_TOKEN));
        // 保存刷新token
        stringRedisTemplate.opsForHash().put(JWT_CACHE_KEY + userId, REFRESH_TOKEN, tokenMap.get(REFRESH_TOKEN));
        // 设置redis中的过期时间是token的2倍
        stringRedisTemplate.expire(userId, EXPIRATION * 2, TimeUnit.MILLISECONDS);
    }

    /**
     * 根据用户id或用户名构建tokenMap
     * @param userId
     * @param username
     * @return
     */
    private Map<String, Object> buildToken(String userId, String username) {
        // 创建accessToken
        String accessToken = generateToken(userId, username, null);
        // 创建refreshToken
        String refreshToken = generateRefreshToken(userId, username, null);
        HashMap<String, Object> tokenMap = new HashMap<>(2);
        tokenMap.put(ACCESS_TOKEN, accessToken);
        tokenMap.put(REFRESH_TOKEN, refreshToken);
        tokenMap.put(EXPIRE_IN, EXPIRATION);
        return tokenMap;
    }


    /**
     * 根据用户名或者用户id从redis中删除原来保存的token，新建token并保存到redis里
     * @param userId
     * @param username
     * @return
     */
    public Map<String, Object> refreshTokenAndGenerateToken(String userId, String username) {
        Map<String, Object> tokenMap = buildToken(userId, username);
        stringRedisTemplate.delete(JWT_CACHE_KEY + userId);
        cacheToken(userId, tokenMap);

        return tokenMap;
    }

    /**
     * 从请求头中取出userId
     * @param request
     * @return
     */
    public String getUserIdFromRequest(HttpServletRequest request) {
        return request.getHeader(USER_ID);
    }

    /**
     * 从redis中删除保存的token
     * @param userId
     * @return
     */
    public static boolean removeToken(String userId) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(JWT_CACHE_KEY + userId));
    }

    /**
     * 根据数据map生成 access token字符串
     * @param userId 用户id
     * @param username 用户名
     * @param payloads 初识容量
     * @return
     */
    public String generateToken(String userId, String username,
                                Map<String,String> payloads) {
        Map<String, Object> claims = buildClaims(userId, username, payloads);;

        return generateToken(claims);
    }

    /**
     * generateToken的调用
     * @param userId
     * @param username
     * @param payloads
     * @return
     */
    private Map<String, Object> buildClaims(String userId, String username, Map<String, String> payloads) {
        int payloadSizes = payloads == null? 0 : payloads.size();

        // 要求初始容量至少有两个
        Map<String, Object> claims = new HashMap<>(payloadSizes + 2);
        claims.put("sub", userId);
        claims.put("username", username);
        claims.put("created", new Date());

        if(payloadSizes > 0){
            claims.putAll(payloads);
        }

        return claims;
    }

    /**
     * 生成 refresh token 令牌
     *
     * @param userId 用户Id或用户名
     * @param payloads 令牌中携带的附加信息
     * @return 令牌
     */
    public String generateRefreshToken(String userId, String username, Map<String,String> payloads) {
        Map<String, Object> claims = buildClaims(userId, username, payloads);

        return generateRefreshToken(claims);
    }

    /**
     * 从令牌中获取用户id
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUserIdFromToken(String token) {
        String userId;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * 从令牌中获取用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = (String) claims.get(USER_NAME);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从令牌中获取用户名，如果只保存了用户名字
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUserFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断令牌是否不存在 redis 中
     *
     * @param token 刷新令牌
     * @return true=不存在，false=存在
     */
    public Boolean isRefreshTokenNotExistCache(String token) {
        String userId = getUserIdFromToken(token);
        String refreshToken = (String)stringRedisTemplate.opsForHash().get(JWT_CACHE_KEY + userId, REFRESH_TOKEN);
        return refreshToken == null || !refreshToken.equals(token);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return true=已过期，false=未过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            //验证 JWT 签名失败等同于令牌过期
            return true;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌是否有效
     *
     * @param token       令牌
     * @param userId  用户Id用户名
     * @return 是否有效
     */
    public Boolean validateToken(String token, String userId) {

        String username = getUserIdFromToken(token);
        return (username.equals(userId) && !isTokenExpired(token));
    }


    // 私有方法

    /**
     * 根据map数据生成令牌
     *
     * @param claims 声明数据
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis()
                + EXPIRATION);
        return Jwts.builder().setClaims(claims) // 添加数据
                .setExpiration(expirationDate) // 设置过期时刻，当前时间+过期时间
                .signWith(SignatureAlgorithm.HS512, SECRET) // 加密签名
                .compact();
    }

    /**
     * 生成刷新令牌 refreshToken，有效期是令牌的 2 倍，方法同上
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateRefreshToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION * 2);
        return Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 从令牌中获取数据声明,验证 JWT 签名
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

}