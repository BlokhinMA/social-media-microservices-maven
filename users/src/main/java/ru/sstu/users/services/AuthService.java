package ru.sstu.users.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sstu.users.models.AuthResponse;
import ru.sstu.users.models.User;
import ru.sstu.users.repositories.UserRepository;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Getter
    private String accessToken;
    private String refreshToken;
    @Getter
    private User user;
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8765/logging?for_audit=true";

    public AuthResponse register(User request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        request.setRole("USER");
        User registeredUser = userRepository.save(request);
        registeredUser.setPassword(null);
        restTemplate.postForObject(url, "Пользователь " + registeredUser + " зарегистрировался", String.class);

        String accessToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse auth(User request) {
        User authorizedUser = userRepository.findByLogin(request.getLogin());

        if (authorizedUser != null && BCrypt.checkpw(request.getPassword(), authorizedUser.getPassword())) {
            accessToken = jwtUtil.generate(String.valueOf(authorizedUser.getId()), authorizedUser.getRole(), "ACCESS");
            refreshToken = jwtUtil.generate(String.valueOf(authorizedUser.getId()), authorizedUser.getRole(), "REFRESH");

            user = authorizedUser;
            authorizedUser.setPassword(null);
            restTemplate.postForObject(url, "Пользователь " + authorizedUser + " вошел в систему", String.class);

            return new AuthResponse(accessToken, refreshToken);
        } else  {
            assert authorizedUser != null;
            restTemplate.postForObject(url, "Кто-то попытался войти с логином " + authorizedUser.getLogin(), String.class);
            return null;
        }
    }

    public void logout(String login) {
        restTemplate.postForObject(url, "Пользователь " + login + " вышел из системы", String.class);
        accessToken = "";
        user = new User();
    }

    public String redirect(String page) {
        if (!accessToken.isEmpty())
            return "redirect:/my_profile";
        return page;
    }

    public String redirectIfNotAuth(String page) {
        if (accessToken.isEmpty())
            return "redirect:/sign_in";
        return page;
    }

}
