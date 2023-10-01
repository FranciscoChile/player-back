package com.outbreak.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.outbreak.model.SongDTO;
import com.outbreak.persistence.MediaStreamRepository;


@Service
public class MediaStreamService {    

    private final String cdnBaseUrl;
    private final String cdnSigninKey;

    MediaStreamRepository mediaStreamRepository;
    
    @Autowired
    public MediaStreamService (
        @Value("${cdn-base-url}") String cdnBaseUrl, 
        @Value("${cdn-signin-key}") String cdnSigninKey, 
        MediaStreamRepository mediaStreamRepository) {
        this.mediaStreamRepository = mediaStreamRepository;
        this.cdnBaseUrl = cdnBaseUrl;
        this.cdnSigninKey = cdnSigninKey;
    }
  
    public List<SongDTO> getAlbumIndex(String idAlbum) {
        return mediaStreamRepository.getAlbumIndex(idAlbum);
    }

    public String getSigninURL(String name) throws InvalidKeyException, NoSuchAlgorithmException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        Date tomorrow = cal.getTime();

        //Key env var CDN_SIGNIN_KEY
        System.out.println("cdnBaseUrl " + cdnBaseUrl);
        System.out.println("cdnSigninKey " + cdnSigninKey);
        String base64String = System.getenv(cdnSigninKey);
        byte[] keyBytes = Base64.getUrlDecoder().decode(base64String);        

        return signUrl(cdnBaseUrl + name , keyBytes, "outbreak-signingkey", tomorrow);
    }

    public static String signUrl(String url,
        byte[] key,
        String keyName,
        Date expirationTime)
        throws InvalidKeyException, NoSuchAlgorithmException {

        final long unixTime = expirationTime.getTime() / 1000;

        String urlToSign = url
        + (url.contains("?") ? "&" : "?")
        + "Expires=" + unixTime
        + "&KeyName=" + keyName;

        String encoded = SignedUrls.getSignature(key, urlToSign);
        return urlToSign + "&Signature=" + encoded;
}

}
