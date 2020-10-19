package cn.saberking.jvav.apm.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Map;

/**
 * token工具类
 *
 * @author wyx
 * @version 1.0
 * @date 2020/10/9
 */
public class JwtUtils {
    private static final String TOKEN = "token!Q@W3e4r";

    /**
     * 生成token
     *
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 60);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(TOKEN)).toString();
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取token中payload
     *
     * @param token
     * @return
     */
    public static String getToken(String token) {
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token).getPayload();
    }
}
