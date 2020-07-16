package com.rcfotografia.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rcfotografia.core.entity.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
