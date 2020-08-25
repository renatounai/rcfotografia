package com.rcfotografia.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.ActiveProfiles;

import com.rcfotografia.domain.album.Album;
import com.rcfotografia.domain.album.AlbumRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@AutoConfigureTestDatabase
public class AlbumRestControllerTest {

	@Autowired
	public TestRestTemplate testRestTemplate; 
	
	@Autowired
	public AlbumRepository repository;
	
	private List<Album> albums = new ArrayList<>();
	
	
	@BeforeEach
	public void prepare() {		
		System.out.println(repository.findById(1L).orElseGet(Album::new).getName());
		
		repository.deleteAll();
		
		Album album = new Album(1L, "Ensaio com a Dani Melo");
		album = repository.save(album);
		albums.add(album);
		
		album = new Album(2L, "Ensaio com a Bianca");
		album = repository.save(album);
		albums.add(album);
		
		album = new Album(3L, "Lara");
		album = repository.save(album);
		albums.add(album);
		
		album = new Album(4L, "Kelly");
		album = repository.save(album);
		albums.add(album);
	}
	
	@Test
	public void testGetAlbums() {
		ResponseEntity<Album[]> response = testRestTemplate.getForEntity("/albums", Album[].class);
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().length, albums.size());
	}
		
	@Test
	public void testGetAlbumsNoContent() {
		repository.deleteAll();
		
		ResponseEntity<Album[]> response = testRestTemplate.getForEntity("/albums", Album[].class);
		assertNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void testGetById() {
		Album album = albums.stream().findFirst().orElseThrow();
		
		ResponseEntity<Album> response = testRestTemplate.getForEntity("/albums/{id}", Album.class, album.getId());
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), album);
	}
	
	@Test
	public void testNotFound() {
		Album album = albums.stream().findFirst().orElseThrow();
		album.setId(album.getId() + 200);
		ResponseEntity<Album> response = testRestTemplate.getForEntity("/albums/{id}", Album.class, album.getId());		
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testGetByIdCheckName() {
		Album album = albums.stream().filter(a -> a.getName().equals("Ensaio com a Dani Melo")).findAny().orElseThrow();
		
		ResponseEntity<Album> response = testRestTemplate.getForEntity("/albums/{id}", Album.class, album.getId());		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getName(), "Ensaio com a Dani Melo");
	}

	@Test
	public void testInsert() {
		Album album = new Album();
		album.setName("Ensaio com a Élen");
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums", HttpMethod.PUT, new HttpEntity<>(album), Album.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getInclusao());
		assertEquals(response.getBody().getName(), album.getName());
		assertTrue(response.getBody().getId() > 0);		
	}
	
	@Test
	public void testInsertWithoutName() {
		Album album = new Album();
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums", HttpMethod.PUT, new HttpEntity<>(album), Album.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getInclusao());
		assertNull(response.getBody().getName());
		assertTrue(response.getBody().getId() > 0);		
	}
	
	@Test
	public void testUpdate() {
		Album album = albums.stream().findFirst().orElseThrow();
		album.setName("Ensaio da Luana");
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums/{id}", HttpMethod.POST, new HttpEntity<>(album), Album.class, album.getId());
		
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	@Test
	public void testDelete() {
		Album album = albums.stream().findFirst().orElseThrow();
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums/{id}", HttpMethod.DELETE, new HttpEntity<>(album), Album.class, album.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void testDeleteNotFound() {
		Album album = albums.stream().findFirst().orElseThrow();
		album.setId(album.getId() + 200);
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums/{id}", HttpMethod.DELETE, new HttpEntity<>(album), Album.class, album.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
