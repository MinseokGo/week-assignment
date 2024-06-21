package study.likelionbeweekly.week7.comment;

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
import study.likelionbeweekly.week7.comment.dto.CreateCommentRequest;
import study.likelionbeweekly.week7.comment.dto.FindAllCommentsRequest;
import study.likelionbeweekly.week7.comment.dto.FindAllCommentsResponse;
import study.likelionbeweekly.week7.comment.dto.UpdateCommentRequest;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<FindAllCommentsResponse> findAll(@RequestBody FindAllCommentsRequest request) {
        FindAllCommentsResponse response = commentService.findAllComments(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateCommentRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Member member = userDetails.member();
        commentService.createComment(member, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id,
                                         @RequestBody UpdateCommentRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Member member = userDetails.member();
        commentService.updateComment(id, member, request);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Member member = userDetails.member();
        commentService.deleteComment(id, member);
        return ResponseEntity.ok().body("ok");
    }
}
