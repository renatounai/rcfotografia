package com.rcfotografia.core.service;

import com.rcfotografia.core.NotFoundException;
import com.rcfotografia.core.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseService <T extends BaseEntity, I, R extends JpaRepository<T, I>> {
    private final R repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findByIdQuitely(I id) {
        return repository.findById(id);
    }

    public T findById(I id) {
        return findByIdQuitely(id).orElseThrow( () -> new NotFoundException());
    }

    public T save(T album) {
        return repository.save(album);
    }

    public void delete(I id) {
        repository.delete(findById(id));
    }
}
