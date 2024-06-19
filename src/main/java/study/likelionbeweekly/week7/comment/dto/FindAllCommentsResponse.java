package study.likelionbeweekly.week7.comment.dto;

import java.util.List;
import study.likelionbeweekly.week7.comment.Comment;

public record FindAllCommentsResponse(Long postId, List<FoundComment> comments) {

    public static FindAllCommentsResponse of(List<Comment> comments) {
        return new FindAllCommentsResponse(
                comments.get(0).getPost().getId(),
                comments.stream()
                        .map(comment ->
                                new FoundComment(
                                        comment.getId(),
                                        comment.getContent(),
                                        comment.getMember().getId(),
                                        comment.getMember().getName()))
                        .toList());
    }

    private record FoundComment(Long id, String content, Long memberId, String memberName) {
    }
}
