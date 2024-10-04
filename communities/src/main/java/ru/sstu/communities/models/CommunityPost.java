package ru.sstu.communities.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityPost {

    private int id;
    private String postText;
    private LocalDateTime creationTimeStamp;
    private String authorLogin;
    private int communityId;

}
