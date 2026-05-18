package hr.tvz.watchnext.watchnextapp.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private long tokenValiditySeconds;
    private String base64Secret;

    public long getTokenValiditySeconds() { return tokenValiditySeconds; }
    public void setTokenValiditySeconds(long tokenValiditySeconds) { this.tokenValiditySeconds = tokenValiditySeconds; }

    public String getBase64Secret() { return base64Secret; }
    public void setBase64Secret(String base64Secret) { this.base64Secret = base64Secret; }
}