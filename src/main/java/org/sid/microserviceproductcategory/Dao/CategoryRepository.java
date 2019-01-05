package org.sid.microserviceproductcategory.Dao;

import org.sid.microserviceproductcategory.Entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoryRepository extends MongoRepository<Category, String> {

    Category findByName(String name);
}
