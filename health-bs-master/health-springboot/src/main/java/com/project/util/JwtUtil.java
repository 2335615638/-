package com.project.util;

import cn.hutool.core.lang.UUID;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @Author: zhoujinchuan
 * @Description:  (JWT工具类)
 * @Date: 2022-09-16 10:41
 */
public class JwtUtil {

    /**
     * 有效期为一个小时
     */
    public static Long JWT_TTL = 360000L;

    /**
     * 设置秘钥明文
     */
    public static String JWT_KEY = "zjc";

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成jtw
     *
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject,  getUUID());
        return builder.compact();
    }


    private static JwtBuilder getJwtBuilder(String subject, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return Jwts.builder()
                //唯一的ID
                .setId(uuid)
                // 主题  可以是JSON数据
                .setSubject(subject)
                // 签发者
                .setIssuer(JWT_KEY)
                // 签发时间
                .setIssuedAt(now)
                //使用HS256对称加密算法签名, 第二个参数为秘钥
                .signWith(signatureAlgorithm, secretKey);
    }

    /**
     * 创建token
     *
     * @param id
     * @param subject
     * @return
     */
    public static String createJWT(String id, String subject) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, id);
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        Claims claims;
        try {
            claims = Jwts.parser()
                    // 设置标识名
                    .setSigningKey(secretKey)
                    //解析token
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }


}
