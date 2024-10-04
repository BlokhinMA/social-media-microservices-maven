package ru.sstu.apigateway.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

/*@Configuration
@EnableWebFluxSecurity*/
public class SecurityConfig {

    /*@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .authorizeExchange((requests) -> requests
                        .pathMatchers("/get_token", "/login").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin((form) -> form
                        .loginPage("/login")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                )
                .cors((cors -> cors
                        .configurationSource(corsConfigurationSource())
                ))
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(8);
    }*/

}
