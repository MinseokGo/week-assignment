package study.likelionbeweekly.week7.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.likelionbeweekly.week7.member.Member;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/parse")
    public ResponseEntity<String> parse(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Member member = jwtService.parse(token);
        log.info("member={}", member);
        return ResponseEntity.ok().body("ok");
    }
}
