package study.likelionbeweekly.week7.comment;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.comment.dto.CreateCommentRequest;
import study.likelionbeweekly.week7.comment.dto.FindAllCommentsRequest;
import study.likelionbeweekly.week7.comment.dto.FindAllCommentsResponse;
import study.likelionbeweekly.week7.comment.dto.UpdateCommentRequest;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.member.MemberRepository;
import study.likelionbeweekly.week7.post.Post;
import study.likelionbeweekly.week7.post.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public FindAllCommentsResponse findAllComments(FindAllCommentsRequest request) {
        Long postId = request.postId();
        List<Comment> comments = commentRepository.findByPostId(postId);
        return FindAllCommentsResponse.of(comments);
    }

    @Transactional
    public void createComment(CreateCommentRequest request) {
        Member member = getMember(request);
        Post post = getPost(request);

        String content = request.content();
        Comment comment = new Comment(content, member, post);
        commentRepository.save(comment);
    }

    private Member getMember(CreateCommentRequest request) {
        Long memberId = request.memberId();
        return memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Post getPost(CreateCommentRequest request) {
        Long postId = request.postId();
        return postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateComment(Long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        String updateContent = request.content();
        comment.setContent(updateContent);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        comment.setDeleted(true);
    }
}
