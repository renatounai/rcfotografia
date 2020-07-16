package com.rcfotografia.core.repository;

import com.rcfotografia.core.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
