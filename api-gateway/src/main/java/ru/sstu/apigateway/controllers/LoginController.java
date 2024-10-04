package ru.sstu.apigateway.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import ru.sstu.apigateway.services.CustomUserDetailsService;

import java.security.Principal;

/*@RestController
@Log4j2
@RequiredArgsConstructor*/
@Log4j2
@RestController
public class LoginController {

    /*private final CustomUserDetailsService customUserDetailsService;
    //private final CsrfToken csrfToken;
    //private CsrfTokenRepository csrfTokenRepository;

    @GetMapping("/get_username")
    public String getPrincipal() {
        return customUserDetailsService.getUsername();
    }

    @GetMapping("/get_token")
    public String getToken(ServerWebExchange exchange) {
        String token = exchange.getAttribute(CsrfToken.class.getName());
        log.error(token);
        return exchange.getAttribute(CsrfToken.class.getName());
    }*/

    /*@GetMapping("/**")
    public void forLogger(ServerWebExchange exchange) {
        String s = exchange.getRequest().getURI().getPath();
        log.error(s);
    }*/

}
