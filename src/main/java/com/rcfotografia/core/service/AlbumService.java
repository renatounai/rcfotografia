package com.rcfotografia.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcfotografia.core.NotFoundException;
import com.rcfotografia.core.entity.Album;
import com.rcfotografia.core.repository.AlbumRepository;

@Service
public class AlbumService {

	private @Autowired AlbumRepository repository;

    public List<Album> findAll() {
        return repository.findAll();
    }
    
    public Optional<Album> findByIdQuitely(Integer id) {
        return repository.findById(id);
    }

    public Album findById(Integer id) {
    	List<Album> findAll = repository.findAll();
    	
        return findByIdQuitely(id).orElseThrow( () -> new NotFoundException());
    }

    public Album save(Album album) {
        return repository.save(album);
    }

    public void delete(Integer id) {
        repository.delete(findById(id));
    }


}
