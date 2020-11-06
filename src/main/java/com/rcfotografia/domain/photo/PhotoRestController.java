package com.rcfotografia.domain.photo;

import java.io.IOException;
import java.util.List;

import org.apache.commons.imaging.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rcfotografia.domain.BaseRestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoRestController extends BaseRestController {

	@Autowired
    private final PhotoService service;

    @GetMapping
    public ResponseEntity<List<Photo>> getAlbuns() {
        List<Photo> list = service.findAll();
        return write(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getAlbuns(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Photo> insert(@RequestBody Photo photo) {
        photo.setId(null);
        return new ResponseEntity<>(service.save(photo), HttpStatus.CREATED);

    }
    
    @PostMapping("/upload")
    public void upload(@RequestParam("photo")MultipartFile photo) throws IOException, ImageReadException {
    	service.upload(photo);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Photo photo, @PathVariable String id) {
        Photo currentPhoto = service.findById(id);
        currentPhoto.setArchieved(currentPhoto.getArchieved());
        service.save(currentPhoto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}
