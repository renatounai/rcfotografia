package com.rcfotografia.domain.photo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.PastOrPresent;

import com.rcfotografia.domain.BaseEntity;

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
public class Photo extends BaseEntity<String> {
	
	@Id
    @EqualsAndHashCode.Include
	private String id;
	
	@PastOrPresent
	private LocalDateTime archieved;
}
