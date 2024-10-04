package ru.sstu.communities.models;

import lombok.Data;

import java.util.List;

@Data
public class Community {

    private int id;
    private String name;
    private String creatorLogin;
    private List<CommunityMember> members;
    private List<CommunityPost> posts;
    private boolean isMember;

}
