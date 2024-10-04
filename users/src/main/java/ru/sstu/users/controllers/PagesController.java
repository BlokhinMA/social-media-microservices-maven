package ru.sstu.users.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import ru.sstu.users.models.User;
import ru.sstu.users.services.AuthService;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class PagesController {

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



    @GetMapping()
    public String index() {
        return authService.redirect("index");
    }

    @GetMapping("/sign_up")
    public String signUp() {
        return authService.redirect("sign_up");
    }

    @GetMapping("/sign_in")
    public String signIn() {
        return authService.redirect("sign_in");
    }

    @GetMapping("/my_profile")
    public String myProfile() {
        return authService.redirectIfNotAuth("my_profile");
    }

    @GetMapping("/profile/{login}")
    public String profile(@PathVariable String login) {
        if (token().isEmpty())
            return "redirect:/sign_in";
        if (Objects.equals(login, authService.getUser().getLogin()))
            return "redirect:/my_profile";
        return "profile";
    }



    @GetMapping("/my_albums")
    public String myAlbums(Model model) {
        String x = getData(model, "albums", "albums");
        if (x != null) return x;
        return "my_albums";
    }

    @GetMapping("/album/{id}")
    public String album(@PathVariable int id, Model model) {
        String x = getData(model, "albums/" + id, "album");
        if (x != null) return x;
        return "album";
    }

    @GetMapping("/albums/{userLogin}")
    public String albums(@PathVariable String userLogin) {
        if (token().isEmpty())
            return "redirect:/sign_in";
        if (Objects.equals(userLogin, authService.getUser().getLogin()))
            return "redirect:/my_albums";
        return "albums";
    }

    @GetMapping("/find_albums")
    public String findAlbums() {
        return authService.redirectIfNotAuth("find_albums");
    }

    @GetMapping("/find_photos")
    public String findPhotos() {
        return authService.redirectIfNotAuth("find_photos");
    }

    @GetMapping("/photo/{id}")
    public String photo(@PathVariable int id, Model model) {
        String x = getData(model, "albums/photo/" + id, "photo");
        if (x != null) return x;
        return "photo";
    }



    @GetMapping("/community_management")
    public String communityManagement(Model model) {
        String x = getData(model, "communities/community_management", "communities");
        if (x != null) return x;
        return "community_management";
    }

    @GetMapping("/my_communities")
    public String myCommunities(Model model) {
        String x = getData(model, "communities/communities", "communities");
        if (x != null) return x;
        return "my_communities";
    }

    @GetMapping("/communities/{memberLogin}")
    public String communities(@PathVariable String memberLogin) {
        if (token().isEmpty())
            return "redirect:/sign_in";
        if (Objects.equals(memberLogin, authService.getUser().getLogin()))
            return "redirect:/my_communities";
        return "communities";
    }

    @GetMapping("/community/{id}")
    public String community(@PathVariable String id, Model model) {
        String x = getData(model, "communities/" + id, "community");
        if (x != null) return x;
        return "community";
    }

    @GetMapping("/find_communities")
    public String findCommunities() {
        return authService.redirectIfNotAuth("find_communities");
    }



    private String getData(Model model, String urlMiddle, String attributeName) {
        if (token().isEmpty())
            return "redirect:/sign_in";
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
