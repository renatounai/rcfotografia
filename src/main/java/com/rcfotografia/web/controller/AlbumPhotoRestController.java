package com.rcfotografia.web.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rcfotografia.core.entity.AlbumPhoto;
import com.rcfotografia.core.service.AlbumPhotoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/albumPhotos")
@RequiredArgsConstructor
public class AlbumPhotoRestController extends BaseRestController {

	@Autowired
    private final AlbumPhotoService service;

    @GetMapping
    public ResponseEntity<List<AlbumPhoto>> getAlbuns() {
        List<AlbumPhoto> list = service.findAll();
        return write(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumPhoto> getAlbuns(@PathVariable long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AlbumPhoto> insert(@RequestBody AlbumPhoto albumPhoto) {
        albumPhoto.setId(null);
        return new ResponseEntity<>(service.save(albumPhoto), HttpStatus.CREATED);

    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody AlbumPhoto albumPhoto, @PathVariable long id) {
        AlbumPhoto currentAlbumPhoto = service.findById(id);        
        service.save(currentAlbumPhoto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
