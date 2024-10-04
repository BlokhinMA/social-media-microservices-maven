package ru.sstu.users.services;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.sstu.users.repositories.UserRepository;
import ru.sstu.users.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8765/logging?for_audit=";

    public User getUserByLogin(String login, String userLogin) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.setEmail(null);
            user.setPassword(null);
            restTemplate.postForObject(url + "false", "Пользователь " + userLogin + " обратился к профилю пользователя: " + user, String.class);
            return user;
        }
        return null;
    }

    public List<User> getAll(String login) {
        List<User> users = userRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех пользователей: " + users, String.class);
        return users;
    }

    public User updateRole(int id, String role, String login) {
        User user = userRepository.updateRoleById(id, role);
        restTemplate.postForObject(url + "true", "Админ " + login + " поменял роль пользователя: " + user, String.class);
        return user;
    }

}
