package com.rcfotografia.dominio.photo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rcfotografia.dominio.exception.NotFoundException;

@Service
public class PhotoService {
	private @Autowired PhotoRepository repository;

	@Value("${photos.path}")
	private String photosPath;
	
    public List<Photo> findAll() {
        return repository.findAll();
    }
    
    public Optional<Photo> findByIdQuitely(String id) {
        return repository.findById(id);
    }

    public Photo findById(String id) {
        return findByIdQuitely(id).orElseThrow(NotFoundException::new);
    }

    public Photo save(Photo photo) {
        return repository.save(photo);
    }

    public void delete(String id) {
        repository.delete(findById(id));
    }
    
    
    public void upload(final MultipartFile foto) throws IOException {
    	final String uuid =  UUID.nameUUIDFromBytes(foto.getBytes()).toString();
    	
    	ImageInfo imageInfo;
		try {
			imageInfo = Imaging.getImageInfo(foto.getBytes());
			if (!imageInfo.getFormat().equals(ImageFormats.JPEG) && !imageInfo.getFormat().equals(ImageFormats.PNG)) {
	    		throw new ImageReadException("Formato de imagem inválido!");
	    	}
		} catch (ImageReadException | IOException e) {
			throw new PhotoException("Format inválido! Apenas arquivos JPG ou PNG podem ser enviados.", e);
		}
    	
    	
    	final Photo photo = findByIdQuitely(uuid).orElseGet(() -> {
    		Photo newPhoto = new Photo();
    		newPhoto.setId(uuid);
    		return newPhoto;
    	});
		Files.copy(foto.getInputStream(), Paths.get(photosPath + "/" +uuid), StandardCopyOption.REPLACE_EXISTING);
		photo.setAlteracao(LocalDateTime.now());
		save(photo);
    }
}
