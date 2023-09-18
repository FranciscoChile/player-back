package com.outbreak.web;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.outbreak.model.GetAlbumResponse;
import com.outbreak.service.AlbumService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/outbreak")
public class AlbumController {
    
    public static final int BYTE_RANGE = 128; // increase the byterange from here
    
    AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<GetAlbumResponse> getAlbumIndex(@PathVariable String id) {

        List<?> albumDTO = albumService.getAlbumIndex(id);

        GetAlbumResponse albumResponse = new GetAlbumResponse();
        albumResponse.setData(albumDTO);
        albumResponse.setMessage("Succefully retrieve data");
        albumResponse.setResponseCode(HttpStatus.OK);

        return new ResponseEntity<>(albumResponse, albumResponse.getResponseCode());
    }

    @GetMapping("/audios/{name}")
	public Mono<ResponseEntity<byte[]>> streamAudio(@PathVariable("name") String name) {
		return Mono.just(getContent(name, "audio"));
	}

	private ResponseEntity<byte[]> getContent(String name, String contentTypePrefix) {		
		try {
			byte[] data = albumService.getSongBlob(name);
			return ResponseEntity.status(HttpStatus.OK)
					.header("Content-Type", contentTypePrefix + "/" + "mp3")
					.header("Content-Length", String.valueOf(data.length))
					.body(data);					
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

    
}
