package com.rcfotografia.core.service;

import com.rcfotografia.core.entity.Album;
import com.rcfotografia.core.repository.AlbumRepository;
import org.springframework.stereotype.Service;

@Service
public class AlbumService extends BaseService<Album, Integer, AlbumRepository> {

    public AlbumService(AlbumRepository repository) {
        super(repository);
    }

}
