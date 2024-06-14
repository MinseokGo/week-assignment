package study.likelionbeweekly.week7.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import jakarta.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String LIKE_LION_AUTH_SUBJECT = "like lion auth subject";
    private static final String TOKEN_PROVIDER = "like lion server";

    private static final Long MILLISECONDS = 1000L;
    private static final Long SECONDS = 60L;
    private static final Long MINUTES = 60L;
    private static final Long HOURS = 24L;

    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

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
                                jwtProperties.getSecretKey()
                                        .getBytes(StandardCharsets.UTF_8),
                                SIG.HS256.key()
                                        .build()
                                        .getAlgorithm()))
                .compact();
    }

    @Transactional(readOnly = true)
    public Member parse(String token) {
        String email = getEmail(token);
        return memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    private String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(
                        SIG.HS256.key()
                                .build()
                )
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }
}
