package ru.sstu.albums.models;

import lombok.Data;

import java.util.List;

@Data
public class Album {

    private int id;
    private String name;
    private String userLogin;
    private List<Photo> photos;

}
