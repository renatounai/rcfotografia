package com.rcfotografia.dominio.albumPhoto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.rcfotografia.dominio.BaseEntity;
import com.rcfotografia.dominio.album.Album;
import com.rcfotografia.dominio.photo.Photo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"photo", "album"})})
public class AlbumPhoto extends BaseEntity<Long> {
	
	@Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@JoinColumn(name = "photo")
	@ManyToOne(optional = false, fetch = FetchType.LAZY) 
	private Photo photo;

	@JoinColumn(name = "album")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Album album;
}
