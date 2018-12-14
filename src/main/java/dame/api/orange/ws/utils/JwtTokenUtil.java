package dame.api.orange.ws.utils;

import dame.api.orange.ws.entities.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private String CLAM_KEY_USERNAME = "sub";
    private String CLAM_KEY_AUDIENCE = "audience";
    private String CLAM_KEY_CDREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    public String getUsernameFromToken( String token) {
        String username = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();

        }
        catch (Exception e){
            username = null;
        }
        return username;
    }

    private  Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }
        catch (Exception e){
            claims =null;
        }
        return  claims;
    }

    public boolean validateToken(String autToken, UserDetails userDetails) {
        MyUserDetails user =(MyUserDetails) userDetails;
        final String username = getUsernameFromToken(autToken);
        System.out.println("validateToken "+username);
        System.out.println("validateToken "+user.getUsername());
        System.out.println("validateToken "+isTokenExpired(autToken));
        return (username.equals(user.getUsername() ) && !isTokenExpired(autToken));
    }

    private boolean isTokenExpired(String autToken) {
        final Date expiration = getExpirationDateFromToken(autToken);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String autToken) {
        Date expiration = null;
        try {
            final Claims claims = getClaimsFromToken(autToken);
            if(claims!=null){
                expiration = claims.getExpiration();
            }
            else expiration = null;
        }
        catch (Exception e){
            expiration = null;
        }
        return expiration;
    }

    public  String generateToken(MyUserDetails userDetails) {
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put(CLAM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAM_KEY_CDREATED,new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public Date generateExpirationDate() {
        return  new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
