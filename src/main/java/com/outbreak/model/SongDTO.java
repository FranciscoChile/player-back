package com.outbreak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {

    private String id;
    private String songName;
    private String artistName;
    private String idAlbum;
    private int idSongAlbum;
    
}
