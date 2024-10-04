package ru.sstu.albums.repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sstu.albums.models.Album;

import java.util.List;

@Repository
@AllArgsConstructor
public class AlbumRepository {

    private final JdbcTemplate jdbcTemplate;

    public Album save(Album album) {
        return jdbcTemplate.query("call save_album(?, ?)", new BeanPropertyRowMapper<>(Album.class),
                        album.getName(),
                        album.getUserLogin())
                .stream().findAny().orElse(null);
    }

    public List<Album> findAllByUserLogin(String userLogin) {
        return jdbcTemplate.query("call find_albums_by_user_login(?)", new BeanPropertyRowMapper<>(Album.class),
                userLogin);
    }

    public Album findById(int id) {
        return jdbcTemplate.query("call find_album_by_id(?)", new BeanPropertyRowMapper<>(Album.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public Album deleteById(int id) {
        return jdbcTemplate.query("call delete_album_by_id(?)", new BeanPropertyRowMapper<>(Album.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<Album> findAllLikeName(String word) {
        return jdbcTemplate.query("call find_albums_like_name(?)", new BeanPropertyRowMapper<>(Album.class),
                word);
    }

    public List<Album> findAll() {
        return jdbcTemplate.query("call find_albums()", new BeanPropertyRowMapper<>(Album.class));
    }

}
