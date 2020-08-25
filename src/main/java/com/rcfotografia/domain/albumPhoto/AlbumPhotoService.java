package com.rcfotografia.dominio.albumPhoto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcfotografia.dominio.exception.NotFoundException;

@Service
public class AlbumPhotoService {
	private @Autowired AlbumPhotoRepository repository;

    public List<AlbumPhoto> findAll() {
        return repository.findAll();
    }
    
    public Optional<AlbumPhoto> findByIdQuitely(Long id) {
        return repository.findById(id);
    }

    public AlbumPhoto findById(Long id) {
        return findByIdQuitely(id).orElseThrow(NotFoundException::new);
    }

    public AlbumPhoto save(AlbumPhoto photo) {
        return repository.save(photo);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}