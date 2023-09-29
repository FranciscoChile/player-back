package com.outbreak.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.outbreak.model.SongDTO;
import com.outbreak.persistence.MediaStreamRepository;


@Service
public class MediaStreamService {    

    MediaStreamRepository mediaStreamRepository;
    
    @Autowired
    public MediaStreamService (MediaStreamRepository mediaStreamRepository) {
        this.mediaStreamRepository = mediaStreamRepository;
    }
  
    public List<SongDTO> getAlbumIndex(String idAlbum) {
        return mediaStreamRepository.getAlbumIndex(idAlbum);
    }


}
