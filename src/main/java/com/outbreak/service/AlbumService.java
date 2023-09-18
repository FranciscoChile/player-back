package com.outbreak.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.outbreak.model.SongDTO;
import com.outbreak.persistence.AlbumRepository;

@Service
public class AlbumService {
    public static final int BYTE_RANGE = 128; // increase the byterange from here

    AlbumRepository albumRepo;
    
    @Autowired
    public AlbumService (AlbumRepository albumRepo) {this.albumRepo = albumRepo;}

    public byte[] getSongBlob(String name) throws FileNotFoundException, IOException {
        
        byte[] prevContent = null;

        // File file = ResourceUtils.getFile("classpath:outbreak-398318-e24bd2b96e39.json");
        // InputStream in = new FileInputStream(file);

        // GoogleCredentials credentials =
        //     GoogleCredentials.fromStream(in)
        //         .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));    

        // Storage storage = StorageOptions
        // .newBuilder()
        // .setProjectId("outbreak-398318")
        // .setCredentials(credentials)
        // .build()
        // .getService();

        Storage storage = StorageOptions.newBuilder().setProjectId("outbreak-398318").build().getService();

        BlobId blobId = BlobId.of("outbreakofevil", name);    

        Blob blob = storage.get(blobId);
        if (blob != null) {
            System.out.println(new Timestamp(System.currentTimeMillis()));
            prevContent = readByteRange(blob, 0, blob.getSize()-1);
            System.out.println(new Timestamp(System.currentTimeMillis()));
        }

        return prevContent;
    }
    
        public byte[] readByteRange(Blob blob, long start, long end) throws IOException {
		
		try (ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            ReadChannel readChannel = blob.reader();
            InputStream inputStream = Channels.newInputStream(readChannel);
			byte[] data = new byte[BYTE_RANGE];
			int nRead;
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				bufferedOutputStream.write(data, 0, nRead);
			}
			bufferedOutputStream.flush();
			byte[] result = new byte[(int) (end - start) + 1];
			System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
			return result;
		}
	}
  
    public List<SongDTO> getAlbumIndex(String idAlbum) {
        return albumRepo.getAlbumIndex(idAlbum);
    }


}
