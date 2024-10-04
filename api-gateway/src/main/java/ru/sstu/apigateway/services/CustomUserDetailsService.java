package ru.sstu.apigateway.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.sstu.apigateway.models.User;

import java.security.Principal;
import java.util.Objects;

/*@Service
@RequiredArgsConstructor
@Log4j2*/
public class CustomUserDetailsService/* implements ReactiveUserDetailsService*/ {

    /*private final WebClient webClient;
    @Getter
    private String username;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<User> user = webClient.get()
                .uri("/users/auth_{username}", username)
                .retrieve()
                .bodyToMono(User.class);
        //log.error("Пользователь {} зашел в систему", user.block());
        this.username = username;
        return user.cast(UserDetails.class);
    }*/

}
