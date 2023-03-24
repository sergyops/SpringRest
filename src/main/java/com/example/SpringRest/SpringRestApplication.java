package com.example.SpringRest;

import com.example.SpringRest.entities.Laptop;
import com.example.SpringRest.entities.User;
import com.example.SpringRest.repository.LaptopRepository;
import com.example.SpringRest.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringRestApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SpringRestApplication.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);


		Laptop laptop1 = new Laptop(null, "HP", 199.99);
		Laptop laptop2 = new Laptop(null, "Acer", 400);

		repository.save(laptop1);
		repository.save(laptop2);
	}

}
