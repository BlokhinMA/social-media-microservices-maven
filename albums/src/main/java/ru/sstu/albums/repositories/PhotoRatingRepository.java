package ru.sstu.albums.repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sstu.albums.models.PhotoComment;
import ru.sstu.albums.models.PhotoRating;

import java.util.List;

@Repository
@AllArgsConstructor
public class PhotoRatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhotoRating findById(int id) {
        return jdbcTemplate.query("call find_photo_rating_by_id(?)", new BeanPropertyRowMapper<>(PhotoRating.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public PhotoRating findByRatingUserLoginAndPhotoId(String ratingUserLogin, int photoId) {
        return jdbcTemplate.query("call find_photo_rating_by_rating_user_login_and_photo_id(?, ?)", new BeanPropertyRowMapper<>(PhotoRating.class),
                        ratingUserLogin,
                        photoId)
                .stream().findAny().orElse(null);
    }

    public Double calculateAverageRatingByPhotoId(int photoId) {
        return jdbcTemplate.queryForObject("call calculate_average_photo_rating_rating_by_photo_id(?)", Double.class,
                photoId);
    }

    public List<PhotoRating> findAllByPhotoId(int photoId) {
        return jdbcTemplate.query("call find_photo_ratings_by_photo_id(?)", new BeanPropertyRowMapper<>(PhotoRating.class),
                photoId);
    }

    public PhotoRating save(PhotoRating photoRating) {
        return jdbcTemplate.query("call save_photo_rating(?, ?, ?)", new BeanPropertyRowMapper<>(PhotoRating.class),
                        photoRating.isRating(),
                        photoRating.getRatingUserLogin(),
                        photoRating.getPhotoId())
                .stream().findAny().orElse(null);
    }

    public PhotoRating updateRatingById(PhotoRating photoRating) {
        return jdbcTemplate.query("call update_photo_rating_rating_by_id(?, ?)", new BeanPropertyRowMapper<>(PhotoRating.class),
                        photoRating.isRating(),
                        photoRating.getId())
                .stream().findAny().orElse(null);
    }

    public PhotoRating deleteById(int id) {
        return jdbcTemplate.query("call delete_photo_rating_by_id(?)", new BeanPropertyRowMapper<>(PhotoRating.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<PhotoRating> findAll() {
        return jdbcTemplate.query("call find_photo_ratings()", new BeanPropertyRowMapper<>(PhotoRating.class));
    }

}
