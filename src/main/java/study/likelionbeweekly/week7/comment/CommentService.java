package study.likelionbeweekly.week7.comment;

import jakarta.persistence.EntityExistsException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.comment.dto.CreateCommentRequest;
import study.likelionbeweekly.week7.comment.dto.FindAllCommentsRequest;
import study.likelionbeweekly.week7.comment.dto.FindAllCommentsResponse;
import study.likelionbeweekly.week7.comment.dto.UpdateCommentRequest;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.post.Post;
import study.likelionbeweekly.week7.post.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public FindAllCommentsResponse findAllComments(FindAllCommentsRequest request) {
        Long postId = request.postId();
        List<Comment> comments = commentRepository.findByPostId(postId);
        return FindAllCommentsResponse.of(postId, comments);
    }

    @Transactional
    public void createComment(Member member, CreateCommentRequest request) {
        Post post = getPost(request);

        String content = request.content();
        Comment comment = new Comment(content, member, post);
        commentRepository.save(comment);
    }

    private Post getPost(CreateCommentRequest request) {
        Long postId = request.postId();
        return postRepository.findById(postId)
                .orElseThrow(EntityExistsException::new);
    }

    @Transactional
    public void updateComment(Long id, Member other, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        validateCommentAuthor(other, comment);

        String updateContent = request.content();
        comment.setContent(updateContent);
    }

    @Transactional
    public void deleteComment(Long id, Member other) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityExistsException::new);
        validateCommentAuthor(other, comment);

        comment.setDeleted(true);
    }

    private void validateCommentAuthor(Member other, Comment comment) {
        Member member = comment.getMember();
        if (member.equals(other)){
            throw new IllegalArgumentException("mismatched member");
        }
    }
}
