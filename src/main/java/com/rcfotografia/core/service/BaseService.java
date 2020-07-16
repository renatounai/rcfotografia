package com.rcfotografia.core.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcfotografia.core.entity.BaseEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseService <T extends BaseEntity, I, R extends JpaRepository<T, I>> {
    protected final R repository;


}
