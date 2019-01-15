package org.sid.microserviceproductcategory.Web;

import lombok.Data;
import lombok.ToString;
import org.sid.microserviceproductcategory.Dao.CategoryRepository;
import org.sid.microserviceproductcategory.Dao.ProductRepository;
import org.sid.microserviceproductcategory.Entities.Category;
import org.sid.microserviceproductcategory.Entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/customProducts")
    public ResponseEntity<?> postProduct(@RequestBody ProductForm form) {
        Category c = categoryRepository.findById(form.getCategory()).get();
        Product p = productRepository.save(new Product(null, form.getName(), form.getPrice(), c));
        c.getProducts().add(p);
        categoryRepository.save(c);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/customProducts")
    public ResponseEntity<?> putProduct(@RequestBody ProductForm form) {
        form.setCategory(form.getCategory().split("/")[form.getCategory().split("/").length - 1]);
        Product product = productRepository.findById(form.getId()).get();

        Category oldCategory = product.getCategory();
        oldCategory.getProducts().remove(product);
        categoryRepository.save(oldCategory);

        Category newCategory = categoryRepository.findById(form.getCategory()).get();

        product.setName(form.getName());
        product.setCategory(newCategory);
        product.setPrice(form.getPrice());
        productRepository.save(product);

        newCategory.getProducts().add(product);
        categoryRepository.save(newCategory);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

@Data
@ToString
class ProductForm {
    private String id;
    private String name;
    private double price;
    private String category;
}
