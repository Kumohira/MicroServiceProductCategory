package org.sid.microserviceproductcategory.Dao;

import org.sid.microserviceproductcategory.Entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByName(String name);
}
