package com.rcfotografia.domain.albumPhoto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rcfotografia.domain.album.Album;
import com.rcfotografia.domain.photo.Photo;

@Repository
public interface AlbumPhotoRepository extends JpaRepository<AlbumPhoto, Long> {

	List<AlbumPhoto> findByAlbum(Album album);
	
	List<AlbumPhoto> findByPhoto(Photo photo);
	
	
}
