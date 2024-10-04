package ru.sstu.albums.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotoComment {

    private int id;
    private String comment;
    private LocalDateTime commentingTimeStamp;
    private String commentingUserLogin;
    private int photoId;

}
