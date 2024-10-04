package ru.sstu.albums.models;

import lombok.Data;

@Data
public class PhotoTag {

    private int id;
    private String tag;
    private int photoId;

}
