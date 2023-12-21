package val.gord.blogproject.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "val.gord.blog")
public class BlogJWTConfig {
    private String secret = "secret";
    private Long expires = 86400000L ;

    public BlogJWTConfig() {
    }

    public BlogJWTConfig(String secret, Long expires) {
        this.secret = secret;
        this.expires = expires;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
