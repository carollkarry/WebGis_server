package com.xxxx.server.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/*
JWtToken工具类
 */
@Component
@Configuration
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


/*
根据用户信息生产token
@param userDetails
@return
 */
    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }
    /*
    *从token中获取登陆用户名
    * @param token
    * @return
     */
    public String getUserNameFromToken(String token){
        String name;
       try{
           Claims claims= getClaimsFromToken(token);
           name=claims.getSubject();
       }catch (Exception e){
           name=null;
       }
       return name;
    }

    /*
    *验证token是否有效
    * @param token
    * @param userDetails
    * @return
     */

    public  boolean validateToken(String token,UserDetails userDetails){
        String username=getUserNameFromToken(token);
        return username.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }

    /*
    *判定token是否失效
    * @param token
    * @return
     */
    private boolean isTokenExpired(String token) {
       Date expireDate= getExpiredDateFromToken(token);
       return expireDate.before(new Date());
    }

    /*
    *判断token是否可以被刷新
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /*
    *刷新token
     */
    public String refresh(String token)
    {
        Claims claims=getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /*
    *从token获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims=getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /*
   *从token里面获取荷载
   * @param token
   * @return
    */
    private Claims getClaimsFromToken(String token) {
        Claims claims=null;
      try{
          claims=Jwts.parser()
                  .setSigningKey(secret)
                  .parseClaimsJws(token)
                  .getBody();
      }catch (Exception e)
      {
          e.printStackTrace();
      }
        return claims;
    }

    /*
    *根据荷载生产JWT TOKEN
    * @param claims
    * @return
     */

    private String generateToken(Map<String,Object>claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    /*
    *生成token失效时间
    * @return
     */

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

}
