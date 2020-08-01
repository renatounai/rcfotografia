package com.rcfotografia.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rcfotografia.core.entity.Album;
import com.rcfotografia.core.entity.AlbumPhoto;
import com.rcfotografia.core.entity.Photo;

@Repository
public interface AlbumPhotoRepository extends JpaRepository<AlbumPhoto, Long> {

	List<AlbumPhoto> findByAlbum(Album album);
	
	List<AlbumPhoto> findByPhoto(Photo photo);
	
	
}
