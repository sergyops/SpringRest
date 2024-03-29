package com.example.SpringRest.controller;

import com.example.SpringRest.entities.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(){
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK,response.getStatusCode());

        List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println(laptops.size());//return 0 because the DB isn't persistent
        //DB is always created when application start and isn't saved when it is closed
    }

    @DisplayName("findOneByidTest")//to change the display name
    @Test
    void findOneById() {

        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());//return not found because database is empty

    }

    @Test
    void create() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "model": "MSI",
                    "price": 400.0
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        Laptop result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("MSI", result.getModel());
    }

    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //To test this you will need to create a laptop
        String json = """
                {
                    "model": "MSI",
                    "price": 400.0
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        //and then test it
        String jsonupdate = """
                {
                    "id": 1,
                    "model": "Lenovo",
                    "price": 450.0
                }
                """;

        HttpEntity<String> requestUpdate = new HttpEntity<>(jsonupdate,headers);
        ResponseEntity<Laptop> responseUpdate = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT, requestUpdate, Laptop.class);

        Laptop result = responseUpdate.getBody();

        assertEquals(1L, result.getId());
        assertEquals("Lenovo", result.getModel());
    }

    @Test
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //To test this you will need to create a laptop
        String json = """
                {
                    "model": "MSI",
                    "price": 400.0
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        //and then test it
        ResponseEntity<Laptop> responseDelete = testRestTemplate.exchange("/api/laptops/1",HttpMethod.DELETE, HttpEntity.EMPTY,Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT,responseDelete.getStatusCode());
    }

    @Test
    void deleteAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //To test this you will need to create a laptop
        String json = """
                {
                    "model": "MSI",
                    "price": 400.0
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        //and then test it
        ResponseEntity<Laptop> responseDelete = testRestTemplate.exchange("/api/laptops",HttpMethod.DELETE, HttpEntity.EMPTY,Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT,responseDelete.getStatusCode());
    }
}