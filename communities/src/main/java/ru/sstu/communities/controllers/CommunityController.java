package ru.sstu.communities.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.communities.models.Community;
import ru.sstu.communities.models.CommunityMember;
import ru.sstu.communities.models.CommunityPost;
import ru.sstu.communities.services.CommunityService;

@RestController
@RequestMapping("/communities")
@AllArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community_management")
    public ResponseEntity<?> showAllOwn(@RequestParam String login) {
        return ResponseEntity.ok(communityService.showAllOwn(login));
    }

    @GetMapping("/communities")
    public ResponseEntity<?> showAll(@RequestParam String login) {
        return ResponseEntity.ok(communityService.showAll(login));
    }

    @PostMapping("/add_community")
    public ResponseEntity<?> create(@RequestBody Community community) {
        return ResponseEntity.ok(communityService.create(community));
    }

    @DeleteMapping("/delete_community")
    public ResponseEntity<?> delete(@RequestParam int id, @RequestParam String login) {
        return ResponseEntity.ok(communityService.delete(id, login));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable int id, @RequestParam String login) {
        return ResponseEntity.ok(communityService.show(id, login));
    }

    @PostMapping("/join_community")
    public ResponseEntity<?> join(@RequestBody CommunityMember communityMember) {
        return ResponseEntity.ok(communityService.join(communityMember));
    }

    @DeleteMapping("/leave_community")
    public ResponseEntity<?> leave(@RequestBody CommunityMember communityMember) {
        return ResponseEntity.ok(communityService.leave(communityMember));
    }

    @DeleteMapping("/kick_community_member")
    public ResponseEntity<?> kickCommunityMember(@RequestParam int id, @RequestParam String login) {
        return ResponseEntity.ok(communityService.kickCommunityMember(id, login));
    }

    @PostMapping("/add_community_post")
    public ResponseEntity<?> createPost(@RequestBody CommunityPost communityPost) {
        return ResponseEntity.ok(communityService.createPost(communityPost));
    }

    @DeleteMapping("/delete_community_post")
    public ResponseEntity<?> deletePost(@RequestParam int id, @RequestParam String login) {
        return (ResponseEntity<?>) ResponseEntity.ok(/*communityService.deletePost(id, login)*/);
    }

    @GetMapping("/find_communities")
    public ResponseEntity<?> find(@RequestParam String keyword, @RequestParam String login) {
        return ResponseEntity.ok(communityService.find(keyword, login));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam String login) {
        return ResponseEntity.ok(communityService.getAll(login));
    }

    @GetMapping("/all_community_members")
    public ResponseEntity<?> getAllMembers(@RequestParam String login) {
        return ResponseEntity.ok(communityService.getAllMembers(login));
    }

    @GetMapping("/all_community_posts")
    public ResponseEntity<?> getAllPosts(@RequestParam String login) {
        return ResponseEntity.ok(communityService.getAllPosts(login));
    }

}
