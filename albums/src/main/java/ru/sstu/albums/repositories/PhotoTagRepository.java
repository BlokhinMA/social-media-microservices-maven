package ru.sstu.albums.repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sstu.albums.models.PhotoTag;

import java.util.List;

@Repository
@AllArgsConstructor
public class PhotoTagRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhotoTag save(PhotoTag photoTag) {
        return jdbcTemplate.query("call save_photo_tag(?, ?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                        photoTag.getTag(),
                        photoTag.getPhotoId())
                .stream().findAny().orElse(null);
    }

    public List<PhotoTag> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("call find_photo_tags_by_photo_id(?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                photoId);
    }

    public PhotoTag findByTagAndPhotoId(ru.sstu.albums.models.PhotoTag photoTag) {
        return jdbcTemplate.query("call find_photo_tag_by_tag_and_photo_id(?, ?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                        photoTag.getTag(),
                        photoTag.getPhotoId())
                .stream().findAny().orElse(null);
    }

    public PhotoTag deleteById(int id) {
        return jdbcTemplate.query("call delete_photo_tag_by_id(?)", new BeanPropertyRowMapper<>(PhotoTag.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<PhotoTag> findAll() {
        return jdbcTemplate.query("call find_photo_tags()", new BeanPropertyRowMapper<>(PhotoTag.class));
    }

}
