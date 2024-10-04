package ru.sstu.albums.repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sstu.albums.models.Photo;

import java.util.List;

@Repository
@AllArgsConstructor
public class PhotoRepository {

    private final JdbcTemplate jdbcTemplate;

    public Photo save(Photo photo) {
        jdbcTemplate.update("INSERT INTO Photo(name, original_file_name, size, content_type, bytes, album_id) VALUES(?, ?, ?, ?, ?, ?)",
                photo.getName(),
                photo.getOriginalFileName(),
                photo.getSize(),
                photo.getContentType(),
                photo.getBytes(),
                photo.getAlbumId());

        return jdbcTemplate.query("SELECT id, name, original_file_name, size, content_type, creation_time_stamp, album_id FROM Photo ORDER BY id DESC LIMIT 1", new BeanPropertyRowMapper<>(Photo.class))
                .stream().findAny().orElse(null);
    }

    public List<Photo> findAllByAlbumId(int albumId) {
        return jdbcTemplate.query("call find_photos_by_album_id(?)", new BeanPropertyRowMapper<>(Photo.class),
                albumId);
    }

    public Photo findById(int id) {
        return jdbcTemplate.query("call find_photo_by_id(?)", new BeanPropertyRowMapper<>(Photo.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public Photo findEntityById(int id) {
        return jdbcTemplate.query("call find_photo_entity_by_id(?)", new BeanPropertyRowMapper<>(Photo.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public Photo deleteById(int id) {
        return jdbcTemplate.query("call delete_photo_by_id(?)", new BeanPropertyRowMapper<>(Photo.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<Photo> findAllLikeCreationTimeStamp(String word) {
        return jdbcTemplate.query("call find_photos_like_creation_time_stamp(?)", new BeanPropertyRowMapper<>(Photo.class),
                word);
    }

    public List<Photo> findAllLikeTag(String word) {
        return jdbcTemplate.query("call find_photos_like_tag(?)", new BeanPropertyRowMapper<>(Photo.class),
                word);
    }

    public List<Photo> findAllLikeComment(String word) {
        return jdbcTemplate.query("call find_photos_like_comment(?)", new BeanPropertyRowMapper<>(Photo.class),
                word);
    }

    public List<Photo> findAll() {
        return jdbcTemplate.query("call find_photos()", new BeanPropertyRowMapper<>(Photo.class));
    }

}
