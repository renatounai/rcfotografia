package com.rcfotografia.web.controller;

import com.rcfotografia.core.entity.Album;
import com.rcfotografia.core.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albuns")
@RequiredArgsConstructor
public class AlbumRestController extends BaseRestController {

    private final AlbumService service;

    @GetMapping
    public ResponseEntity<List<Album>> getAlbuns() {
        List<Album> list = service.findAll();
        return write(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbuns(@PathVariable int id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Album> insert(@RequestBody Album album) {
        album.setId(null);
        return new ResponseEntity<>(service.save(album), HttpStatus.CREATED);

    }

    @PostMapping("/{id}")
    public void update(@RequestBody Album album, @PathVariable int id) {
        Album albumAtual = service.findById(id);
        albumAtual.setNome(album.getNome());
        service.save(albumAtual);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

}
