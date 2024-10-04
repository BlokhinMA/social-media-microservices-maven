package ru.sstu.users.repositories;

import lombok.AllArgsConstructor;
import ru.sstu.users.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public User save(User user) {
        return jdbcTemplate.query("call save_user(?, ?, ?, ?, ?, ?, ?)", new BeanPropertyRowMapper<>(User.class),
                        user.getLogin(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getBirthDate(),
                        user.getPassword(),
                        user.getRole())
                .stream().findAny().orElse(null);
    }

    public User findByLogin(String login) {
        return jdbcTemplate.query("call find_user_by_login(?)", new BeanPropertyRowMapper<>(User.class),
                        login)
                .stream().findAny().orElse(null);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("call find_users()", new BeanPropertyRowMapper<>(User.class));
    }

    public User updateRoleById(int id, String role) {
        return jdbcTemplate.query("call update_user_role_by_id(?, ?)", new BeanPropertyRowMapper<>(User.class),
                        id,
                        role)
                .stream().findAny().orElse(null);
    }

}
