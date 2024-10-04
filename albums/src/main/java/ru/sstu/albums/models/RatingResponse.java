package ru.sstu.albums.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingResponse {

    private int rating;
    private int id;

}
