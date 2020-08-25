package com.rcfotografia.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rcfotografia.dominio.photo.Photo;
import com.rcfotografia.dominio.photo.PhotoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class PhotoRestControllerTest {

	@Autowired
	public TestRestTemplate testRestTemplate; 
	
	@Autowired
	public PhotoRepository repository;
	
	private List<Photo> photos = new ArrayList<>();
	
	
	@Test
	public void testGetPhotos() {
		ResponseEntity<Photo[]> response = testRestTemplate.getForEntity("/photos", Photo[].class);
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().length, photos.size());
	}
	
	@Test
	public void testGetPhotosNoContent() {
		repository.deleteAll();
		
		ResponseEntity<Photo[]> response = testRestTemplate.getForEntity("/photos", Photo[].class);
		assertNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void testGetById() {
		Photo photo = photos.stream().findFirst().orElseThrow();
		
		ResponseEntity<Photo> response = testRestTemplate.getForEntity("/photos/{id}", Photo.class, photo.getId());
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), photo);
	}
	
	@Test
	public void testNotFound() {
		Photo photo = photos.stream().findFirst().orElseThrow();
		photo.setId(photo.getId() + 200);
		ResponseEntity<Photo> response = testRestTemplate.getForEntity("/photos/{id}", Photo.class, photo.getId());		
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	

	@Test
	public void testInsert() {
		Photo photo = new Photo();
		ResponseEntity<Photo> response = testRestTemplate.exchange("/photos", HttpMethod.PUT, new HttpEntity<>(photo), Photo.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getInclusao());
				
	}
	
	@Test
	public void testUpdate() {
		Photo photo = photos.stream().findFirst().orElseThrow();
		ResponseEntity<Photo> response = testRestTemplate.exchange("/photos/{id}", HttpMethod.POST, new HttpEntity<>(photo), Photo.class, photo.getId());
		
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	@Test
	public void testDelete() {
		Photo photo = photos.stream().findFirst().orElseThrow();
		ResponseEntity<Photo> response = testRestTemplate.exchange("/photos/{id}", HttpMethod.DELETE, new HttpEntity<>(photo), Photo.class, photo.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void testDeleteNotFound() {
		Photo photo = photos.stream().findFirst().orElseThrow();
		photo.setId(photo.getId()+"x");
		ResponseEntity<Photo> response = testRestTemplate.exchange("/photos/{id}", HttpMethod.DELETE, new HttpEntity<>(photo), Photo.class, photo.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
