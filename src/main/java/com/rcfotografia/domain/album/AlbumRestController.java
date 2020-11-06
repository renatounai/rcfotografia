package com.rcfotografia.domain.album;

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

import com.rcfotografia.domain.BaseRestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumRestController extends BaseRestController {

	@Autowired
    private final AlbumService service;

    @GetMapping
    public ResponseEntity<List<Album>> getAlbuns() {
        List<Album> list = service.findAll();
        return write(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbuns(@PathVariable long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Album> insert(@RequestBody Album album) {
        album.setId(null);
        return new ResponseEntity<>(service.save(album), HttpStatus.CREATED);

    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Album album, @PathVariable long id) {
        Album currentAlbum = service.findById(id);
        currentAlbum.setName(album.getName());
        service.save(currentAlbum);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
