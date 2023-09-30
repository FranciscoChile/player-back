package com.outbreak.web;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outbreak.model.GetAlbumResponse;
import com.outbreak.service.MediaStreamService;

@RestController
@RequestMapping("/api/outbreak")
public class MediaStreamController {
    
    MediaStreamService mediaStreamService;

    @Autowired
    public MediaStreamController(MediaStreamService mediaStreamService) {
        this.mediaStreamService = mediaStreamService;
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<GetAlbumResponse> getAlbumIndex(@PathVariable String id) {

        List<?> albumDTO = mediaStreamService.getAlbumIndex(id);

        GetAlbumResponse albumResponse = new GetAlbumResponse();
        albumResponse.setData(albumDTO);
        albumResponse.setMessage("Succefully retrieve data");
        albumResponse.setResponseCode(HttpStatus.OK);

        return new ResponseEntity<>(albumResponse, albumResponse.getResponseCode());
    }

    @GetMapping("/signinurl/{name}")
    public ResponseEntity<String> getSigninURL(@PathVariable String name) throws InvalidKeyException, NoSuchAlgorithmException {

        String signinurl = mediaStreamService.getSigninURL(name);        

        return new ResponseEntity<>(signinurl, HttpStatus.OK);
    }
    
}
