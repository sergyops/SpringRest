package com.example.SpringRest.controller;

import com.example.SpringRest.entities.Laptop;
import com.example.SpringRest.repository.LaptopRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LaptopController {

    LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @GetMapping("/api/laptops")
    public List<Laptop> findAll(){

        //return a list of all laptops stored in DB
        return laptopRepository.findAll();
    }

    @PostMapping("/api/laptops")
    public Laptop create(@RequestBody Laptop book, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));//see who is sending information
        return laptopRepository.save(book);

    }
}
