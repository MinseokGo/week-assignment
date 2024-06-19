package study.likelionbeweekly.week7.post.dto;

import java.time.LocalDateTime;
import study.likelionbeweekly.week7.post.Post;

public record FindPostResponse(Long id,
                               String title,
                               String content,
                               String memberName,
                               LocalDateTime createdAt) {

    public static FindPostResponse of(Post post) {
        return new FindPostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getName(),
                post.getCreatedAt());
    }
}