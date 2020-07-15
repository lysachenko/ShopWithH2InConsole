package service;

import model.Product;

import java.util.List;

public interface ProductService {

    void save(Product product);

    void update(Product product);

    void delete(Product product);

    List<Product> findByName(String name);

    Product findById(long id);

    List<Product> findAll();
}
