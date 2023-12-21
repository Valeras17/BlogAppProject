package val.gord.blogproject.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JWTProvider {
    //read application.properties
    @Value("${val.gord.blog.secret}")
    private String secret;

    @Value("${val.gord.blog.expires}")
    private Long expires;

    //1) key
    //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.ES256);
    private static Key mSecretKey;
    @PostConstruct
    private void init(){
        mSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

    }
    //1) generate JWT
    //1) read from JWT
}
