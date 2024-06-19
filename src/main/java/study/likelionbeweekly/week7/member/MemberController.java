package study.likelionbeweekly.week7.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.likelionbeweekly.week7.security.jwt.CustomUserDetails;
import study.likelionbeweekly.week7.security.jwt.JwtService;
import study.likelionbeweekly.week7.member.dto.JoinMemberRequest;
import study.likelionbeweekly.week7.member.dto.LoginMemberRequest;
import study.likelionbeweekly.week7.member.dto.UpdateMemberRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JwtService jwtService;
    private final MemberService memberService;

    @GetMapping

    @GetMapping
    public ResponseEntity<String> login(@RequestBody LoginMemberRequest request,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {

        memberService.loginMember(request);

        Member member = userDetails.getMember();
        String jwt = jwtService.create(member);
        return ResponseEntity.ok()
                .header("Authorization", jwt)
                .body("ok");
    }

    @PostMapping
    public ResponseEntity<String> join(@RequestBody JoinMemberRequest request) {
        memberService.joinMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id,
                                         @RequestBody UpdateMemberRequest request) {

        memberService.updateMember(id, request);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().body("ok");
    }
}
