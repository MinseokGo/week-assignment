package study.likelionbeweekly.week7.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.likelionbeweekly.week7.post.dto.CreatePostRequest;
import study.likelionbeweekly.week7.post.dto.FindAllPostsResponse;
import study.likelionbeweekly.week7.post.dto.FindPostResponse;
import study.likelionbeweekly.week7.post.dto.UpdatePostRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<FindAllPostsResponse> findAll() {
        FindAllPostsResponse response = postService.findAllPosts();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreatePostRequest request) {
        postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindPostResponse> find(@PathVariable(name = "id") Long id) {
        FindPostResponse response = postService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id,
                                         @RequestBody UpdatePostRequest request) {

        postService.updatePost(id, request);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().body("ok");
    }
}
