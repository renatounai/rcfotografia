package com.rcfotografia.core.entity;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T> {
    public abstract T getId();
    public abstract void setId(T id);

    private LocalDateTime alteracao;
    private LocalDateTime inclusao;
    private LocalDateTime remocao;
    private Boolean excluido = false;

    @PrePersist
    private void prePersist() {
        this.inclusao = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdates() {
        this.alteracao = LocalDateTime.now();
    }
}
