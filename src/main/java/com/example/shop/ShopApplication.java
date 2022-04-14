package com.example.shop;

import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Shop API", version = "1.0", description = "Shop API documentation"))
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ProductService productService) {
		return args -> {
			productService.create(new Product("first product", 12.55));
			productService.create(new Product("second product", 25.4));
			productService.create(new Product("third product", 41.88));
		};
	}

}
