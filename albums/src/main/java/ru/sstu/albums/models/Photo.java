package ru.sstu.albums.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Photo {

    private int id;
    private String name;
    private String originalFileName;
    private long size;
    private String contentType;
    private byte[] bytes;
    private LocalDateTime creationTimeStamp;
    private int albumId;
    private List<PhotoTag> tags;
    private List<PhotoRating> ratings;
    private List<PhotoComment> comments;
    private PhotoRating userRating;
    private int rating;
    private Album album;

}
