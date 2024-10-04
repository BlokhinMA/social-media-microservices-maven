package ru.sstu.communities.models;

import lombok.Data;

@Data
public class CommunityMember {

    private int id;
    private String memberLogin;
    private int communityId;

}
