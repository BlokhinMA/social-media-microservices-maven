package ru.sstu.users.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import ru.sstu.users.models.User;
import ru.sstu.users.services.AuthService;

import java.util.Objects;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminPagesController {

    private final AuthService authService;
    private final RestTemplate restTemplate;

    @ModelAttribute("token")
    public String token() {
        return authService.getAccessToken();
    }

    @ModelAttribute("user")
    public User user() {
        return authService.getUser();
    }



    @GetMapping("/users")
    public String adminUsers(Model model) {
        String x = getData(model, "users/all", "users");
        if (x != null) return x;
        return "admin/users";
    }



    @GetMapping("/albums")
    public String adminAlbums(Model model) {
        String x = getData(model, "albums/all", "albums");
        if (x != null) return x;
        return "admin/albums";
    }



    @GetMapping("/photos")
    public String adminPhotos(Model model) {
        String x = getData(model, "albums/all_photos", "photos");
        if (x != null) return x;
        return "admin/photos";
    }

    @GetMapping("/photo_tags")
    public String adminPhotoTags(Model model) {
        String x = getData(model, "albums/all_photo_tags", "photoTags");
        if (x != null) return x;
        return "admin/photo_tags";
    }

    @GetMapping("/photo_ratings")
    public String adminPhotoRatings(Model model) {
        String x = getData(model, "albums/all_photo_ratings", "photoRatings");
        if (x != null) return x;
        return "admin/photo_ratings";
    }

    @GetMapping("/photo_comments")
    public String adminPhotoComments(Model model) {
        String x = getData(model, "albums/all_photo_comments", "photoComments");
        if (x != null) return x;
        return "admin/photo_comments";
    }



    @GetMapping("/communities")
    public String adminCommunities(Model model) {
        String x = getData(model, "communities/all", "communities");
        if (x != null) return x;
        return "admin/communities";
    }

    @GetMapping("/community_members")
    public String adminCommunityMembers(Model model) {
        String x = getData(model, "communities/all_community_members", "communityMembers");
        if (x != null) return x;
        return "admin/community_members";
    }

    @GetMapping("/community_posts")
    public String adminCommunityPosts(Model model) {
        String x = getData(model, "communities/all_community_posts", "communityPosts");
        if (x != null) return x;
        return "admin/community_posts";
    }







    private String getData(Model model, String urlMiddle, String attributeName) {
        if (token().isEmpty())
            return "redirect:/sign_in";
        if (Objects.equals(user().getRole(), "USER"))
            return "redirect:/my_profile";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<Object> responseType = new ParameterizedTypeReference<>() {};
        String urlBegin = "http://localhost:8765/";
        String urlEnd = "?login=";
        ResponseEntity<Object> responseEntity = restTemplate.exchange(urlBegin + urlMiddle + urlEnd + user().getLogin(), HttpMethod.GET, entity, responseType);
        Object body = responseEntity.getBody();
        model.addAttribute(attributeName, body);
        return null;
    }

}
