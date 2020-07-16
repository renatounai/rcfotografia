package com.rcfotografia.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T> {
    public abstract T getId();
    public abstract void setId(T id);

    private LocalDateTime alteracao;
    private LocalDateTime inclusao;
    private LocalDateTime remocao;
    private Boolean excluido;

    @PrePersist
    private void prePersist() {
        this.inclusao = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdates() {
        this.alteracao = LocalDateTime.now();
    }

    @PreRemove
    private void preRemove() {
        this.remocao = LocalDateTime.now();
    }
}
