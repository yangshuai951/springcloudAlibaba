package com.ext.springcloud.utils;

import com.ext.springcloud.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


/**
 * Jwt工具类
 * 注意点:
 * 1、生成的token, 是可以通过base64进行解密出明文信息
 * 2、base64进行解密出明文信息，修改再进行编码，则会解密失败
 * 3、无法作废已颁布的token，除非改秘钥
 */
public class JwtUtil {

    /**
     * 过期时间
     **/
    private static final long EXPIRE = 1000 * 60 * 30;
    /**
     * 令牌前缀
     **/
    private static final String TOKEN_PREFIX = "auth";

    /**
     * 加密秘钥
     */
    private static final String SECRET = "secret";

    /**
     * SUBJECT
     */
    private static final String SUBJECT = "springcloud";

    /**
     * 根据用户信息，生成令牌
     *
     * @param account
     * @return
     */
    public static String geneJsonWebToken(Account account) {
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", account.getHeadImg())
                .claim("id", account.getId())
                .claim("name", account.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
        token = TOKEN_PREFIX + token;
        return token;
    }

    /**
     * 校验token的方法
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {

        try {

            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

            return claims;

        } catch (Exception e) {
            return null;
        }

    }
}
