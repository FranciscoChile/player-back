package com.outbreak.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.outbreak.model.SongDTO;

@Service
public class MediaStreamRepository {
    

    public List<SongDTO> getAlbumIndex(String idAlbum) {

        List<SongDTO> list =  new ArrayList<>();
        List<SongDTO> resp =  new ArrayList<>();

        SongDTO s1 = new SongDTO("Massakre-RedSeptember.mp3", "Red September", "Massakre", "1", 1, 7937789);
        SongDTO s2 = new SongDTO("Pirosaint-NewGod.mp3", "New God", "Pirosaint", "1", 2, 0);
        SongDTO s3 = new SongDTO("TheShrink-BreakNewWorld.mp3", "Break New World", "The Shrink", "1", 3, 0);
        SongDTO s4 = new SongDTO("Hellfire-KilledByTheAxe.mp3", "Killed By The Axe", "Hellfire", "1", 4, 0);
        SongDTO s5 = new SongDTO("Nuclear-MindfuckedCS.mp3", "Mindfucked C.S.", "Nuclear", "1", 5, 0);
        SongDTO s6 = new SongDTO("Necrosis-TheWeakest.mp3", "The Weakest", "Necrosis", "1", 6, 0);

        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);

        if (idAlbum != null && idAlbum.length() > 0) {
            resp = list.stream()
            .filter(a -> a.getIdAlbum().equals(idAlbum))
            .collect(Collectors.toList());
        }
        
        return resp;

    }

}
