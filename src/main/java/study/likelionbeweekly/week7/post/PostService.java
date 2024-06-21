package study.likelionbeweekly.week7.post;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.likelionbeweekly.week7.member.Member;
import study.likelionbeweekly.week7.member.MemberRepository;
import study.likelionbeweekly.week7.post.dto.CreatePostRequest;
import study.likelionbeweekly.week7.post.dto.FindAllPostsResponse;
import study.likelionbeweekly.week7.post.dto.FindPostResponse;
import study.likelionbeweekly.week7.post.dto.UpdatePostRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createPost(CreatePostRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(EntityNotFoundException::new);

        String createTitle = request.title();
        String createContent = request.content();
        Post post = new Post(createTitle, createContent, member);
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
    public void updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        String updateTitle = request.title();
        post.setTitle(updateTitle);

        String updateContent = request.content();
        post.setContent(updateContent);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.setDeleted(true);
    }
}