package com.rcfotografia.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BaseRestController {

    public <T> ResponseEntity<List<T>> write(List<T> list) {
        if (list.isEmpty()) {
            return new ResponseEntity<List<T>>(list, HttpStatus.NO_CONTENT) ;
        } else {
            return new ResponseEntity<List<T>>(list, HttpStatus.OK) ;
        }

    }
}
