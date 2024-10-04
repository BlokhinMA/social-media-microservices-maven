package ru.sstu.communities.repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sstu.communities.models.CommunityPost;

import java.util.List;

@Repository
@AllArgsConstructor
public class CommunityPostRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommunityPost save(CommunityPost communityPost) {
        return jdbcTemplate.query("call save_community_post(?, ?, ?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                        communityPost.getPostText(),
                        communityPost.getAuthorLogin(),
                        communityPost.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public List<CommunityPost> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("call find_community_posts_by_community_id(?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                communityId);
    }

    public CommunityPost deleteById(int id) {
        return jdbcTemplate.query("call delete_community_post_by_id(?)", new BeanPropertyRowMapper<>(CommunityPost.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<CommunityPost> findAll() {
        return jdbcTemplate.query("call find_community_posts()", new BeanPropertyRowMapper<>(CommunityPost.class));
    }

}
