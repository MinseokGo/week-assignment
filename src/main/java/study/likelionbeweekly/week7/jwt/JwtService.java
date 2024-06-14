package study.likelionbeweekly.week7.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@Service
@ConfigurationProperties(prefix = "jwt")
public class JwtService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String LIKE_LION_AUTH_SUBJECT = "like lion auth subject";
    private static final String TOKEN_PROVIDER = "like lion server";

    private static final Long MILLISECONDS = 1000L;
    private static final Long SECONDS = 60L;
    private static final Long MINUTES = 60L;
    private static final Long HOURS = 24L;

    private String secretKey;

    public String create(String email) {
        return TOKEN_PREFIX + Jwts.builder()
                .subject(LIKE_LION_AUTH_SUBJECT)
                .issuer(TOKEN_PROVIDER)
                .audience()
                .and().id(email)
                .claim("email", email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + MILLISECONDS * SECONDS * MINUTES * HOURS))
                .signWith(
                        new SecretKeySpec(
                                secretKey.getBytes(StandardCharsets.UTF_8),
                                SIG.HS256.key()
                                        .build()
                                        .getAlgorithm()))
                .compact();
    }
}
