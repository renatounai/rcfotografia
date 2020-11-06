package com.rcfotografia.domain;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseRestController {

    public <T> ResponseEntity<List<T>> write(List<T> list) {
        if (list.isEmpty()) {
            return new ResponseEntity<List<T>>(list, HttpStatus.NO_CONTENT) ;
        } else {
            return new ResponseEntity<List<T>>(list, HttpStatus.OK) ;
        }

    }
}
