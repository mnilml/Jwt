package com.lml.jwtlogin.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类
 * @author yanglingcong
 * @date 2021/12/31 11:24 AM
 */
public class JwtUtils {
    private  static  final String  secret="##@$%@#S#WS";


    /**
     * 生成token
     * @author yanglingcong
     * @date 2021/12/31 11:23 AM
     * @param map
     * @return String
     */
    public static  String getToken(Map<String,String> map)
    {
        Calendar instance=Calendar.getInstance();
        //默认1小时过期
        instance.add(Calendar.HOUR,1);
        //创建JWT
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //指定令牌过期时间
        builder.withExpiresAt(instance.getTime());

        String token=builder.sign(Algorithm.HMAC256(secret));
        return token;
    }

    /**
     * 验证token
     * @author yanglingcong
     * @date 2021/12/31 11:26 AM
     * @param token
     */
    public  static  DecodedJWT  verify(String token) {
        return  JWT.require(Algorithm.HMAC256(secret)).build().verify(token);

    }


}
