package dao;

import model.Product;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final ProductDaoImpl singleton = new ProductDaoImpl();

    private ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        return singleton;
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public Product findById(long id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
