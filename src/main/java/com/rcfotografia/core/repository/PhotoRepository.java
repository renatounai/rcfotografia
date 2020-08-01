package com.rcfotografia.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rcfotografia.core.entity.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String>{

}
