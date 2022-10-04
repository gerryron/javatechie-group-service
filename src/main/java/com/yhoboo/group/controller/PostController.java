package com.yhoboo.group.controller;

import com.yhoboo.group.entity.Post;
import com.yhoboo.group.entity.PostStatus;
import com.yhoboo.group.repository.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostRespository postRespository;

    @PostMapping("/create")
    public String createPost(@RequestBody Post post, Principal principal) {
        post.setStatus(PostStatus.PENDING);
        post.setUserName(principal.getName());
        postRespository.save(post);
        return principal.getName()
                + " Your post published successfully, Required ADMIN/ MODERATOR Action!";
    }

    @GetMapping("/approve/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approvePost(@PathVariable int postId) {
        Post post = postRespository.findById(postId).get();
        post.setStatus(PostStatus.APPROVED);
        postRespository.save(post);
        return "Post Approved!";
    }

    @GetMapping("/approveAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approveAll() {
        postRespository.findAll().stream()
                .filter(post -> post.getStatus().equals(PostStatus.PENDING))
                .forEach(post -> {
                    post.setStatus(PostStatus.APPROVED);
                    postRespository.save(post);
                });
        return "All Post Approved!";
    }

    @GetMapping("/reject/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String rejectPost(@PathVariable int postId) {
        Post post = postRespository.findById(postId).get();
        post.setStatus(PostStatus.REJECTED);
        postRespository.save(post);
        return "Post Rejected!";
    }

    @GetMapping("/rejectAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String rejectAll() {
        postRespository.findAll().stream()
                .filter(post -> post.getStatus().equals(PostStatus.PENDING))
                .forEach(post -> {
                    post.setStatus(PostStatus.REJECTED);
                    postRespository.save(post);
                });
        return "All Post Rejected!";
    }

    @GetMapping("/viewAll")
    public List<Post> viewAll() {
        return postRespository.findAll().stream()
                .filter(post -> post.getStatus().equals(PostStatus.APPROVED))
                .collect(Collectors.toList());
    }
}
