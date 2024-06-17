package study.likelionbeweekly.week7.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.likelionbeweekly.week7.member.Member;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/parse")
    public void parse(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Member member = jwtService.parse(token);
        System.out.println("member(" + member.getId() + ", " + member.getEmail() + ")");
    }
}
