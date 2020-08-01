package com.rcfotografia.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.rcfotografia.core.entity.Album;
import com.rcfotografia.core.repository.AlbumRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class AlbumRestControllerTest {

	@Autowired
	public TestRestTemplate testRestTemplate; 
	
	@Autowired
	public AlbumRepository repository;
	
	private List<Album> albuns = new ArrayList<>();
	
	@Before
	public void prepare() {
		repository.deleteAll();
		
		Album album = new Album(1, "Ensaio com a Dani Melo");
		album = repository.save(album);
		albuns.add(album);
		
		album = new Album(2, "Ensaio com a Bianca");
		album = repository.save(album);
		albuns.add(album);
		
		album = new Album(3, "Lara");
		album = repository.save(album);
		albuns.add(album);
		
		album = new Album(4, "Kelly");
		album = repository.save(album);
		albuns.add(album);
	}
	
	@Test
	public void testGetAlbuns() {
		ResponseEntity<Album[]> response = testRestTemplate.getForEntity("/albums", Album[].class);
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().length, 4);
	}
	
	@Test
	public void testGetAlbunsNoContent() {
		repository.deleteAll();
		
		ResponseEntity<Album[]> response = testRestTemplate.getForEntity("/albums", Album[].class);
		assertNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void testGetById() {
		Album album = albuns.stream().findFirst().orElseThrow();
		
		ResponseEntity<Album> response = testRestTemplate.getForEntity("/albums/{id}", Album.class, album.getId());
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), album);
	}
	
	@Test
	public void testNotFound() {
		ResponseEntity<Album> response = testRestTemplate.getForEntity("/albums/20", Album.class);		
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testGetByIdCheckName() {
		Album album = albuns.stream().filter(a -> a.getName().equals("Ensaio com a Dani Melo")).findAny().orElseThrow();
		
		ResponseEntity<Album> response = testRestTemplate.getForEntity("/albums/{id}", Album.class, album.getId());		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
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
		Album album = albuns.stream().findFirst().orElseThrow();
		album.setName("Ensaio da Luana");
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums/{id}", HttpMethod.POST, new HttpEntity<>(album), Album.class, album.getId());
		
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	@Test
	public void testDelete() {
		Album album = albuns.stream().findFirst().orElseThrow();
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums/{id}", HttpMethod.DELETE, new HttpEntity<>(album), Album.class, album.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void testDeleteNotFound() {
		Album album = albuns.stream().findFirst().orElseThrow();
		album.setId(album.getId() + 200);
		ResponseEntity<Album> response = testRestTemplate.exchange("/albums/{id}", HttpMethod.DELETE, new HttpEntity<>(album), Album.class, album.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
