package study.likelionbeweekly.week7.post;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.post.dto.CreatePostRequest;
import study.likelionbeweekly.week7.post.dto.FindAllPostsResponse;
import study.likelionbeweekly.week7.post.dto.FindPostResponse;
import study.likelionbeweekly.week7.post.dto.UpdatePostRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void createPost(CreatePostRequest request, Member other) {
        String createTitle = request.title();
        String createContent = request.content();

        Post post = new Post(createTitle, createContent, other);
        postRepository.save(post);
    }

    public FindAllPostsResponse findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return FindAllPostsResponse.of(posts);
    }

    public FindPostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return FindPostResponse.of(post);
    }

    @Transactional
    public void updatePost(Long id, Member other, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        validatePostAuthor(other, post);

        String updateTitle = request.title();
        String updateContent = request.content();

        if (StringUtils.hasText(updateTitle)) {
            post.setTitle(updateTitle);
        }
        if (StringUtils.hasText(updateContent)) {
            post.setContent(updateContent);
        }
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id, Member other) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        validatePostAuthor(other, post);

        post.setDeleted(true);
    }

    private void validatePostAuthor(Member other, Post post) {
        Member member = post.getMember();
        if (!member.equals(other)) {
            throw new IllegalArgumentException("mismatched member");
        }
    }
}