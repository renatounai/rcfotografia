package com.rcfotografia.dominio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T>  {
    public abstract T getId();
    public abstract void setId(T id);

    private LocalDateTime alteracao;
    
    @NotNull @Column(updatable = false)
    private LocalDateTime inclusao;
    
    @PrePersist
    private void prePersist() {
        this.inclusao = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdates() {
        this.alteracao = LocalDateTime.now();
    }
}
