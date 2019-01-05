package org.sid.microserviceproductcategory;

import org.sid.microserviceproductcategory.Dao.CategoryRepository;
import org.sid.microserviceproductcategory.Dao.ProductRepository;
import org.sid.microserviceproductcategory.Entities.Category;
import org.sid.microserviceproductcategory.Entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootApplication
public class MicroServiceProductCategoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceProductCategoryApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {

            categoryRepository.deleteAll();
            productRepository.deleteAll();

            Stream.of("Phones", "Keyboards").forEach(category -> {
                categoryRepository.save(new Category(null, category, new ArrayList<>()));
            });

            //Category c = categoryRepository.findOne(Query.query(Criteria.where("_id").is(55)), Category.class);
            Category phones = categoryRepository.findByName("Phones");
            Category keyboards = categoryRepository.findByName("Keyboards");

            Stream.of("IphoneX", "SamsungS10", "Pocophone").forEach(product -> {
                Product product1 = productRepository.save(new Product(null, product, Math.random() * 1000, phones));
                phones.getProducts().add(product1);
                categoryRepository.save(phones);
            });
            Stream.of("Razer", "Logitech").forEach(product -> {
                Product product2 = productRepository.save(new Product(null, product, Math.random() * 1000, keyboards));
                keyboards.getProducts().add(product2);
                categoryRepository.save(keyboards);
            });

            categoryRepository.findAll().forEach(System.out::println);
            productRepository.findAll().forEach(System.out::println);

        };
    }
}

