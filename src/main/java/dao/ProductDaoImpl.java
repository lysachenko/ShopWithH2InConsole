package dao;

import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProductDaoImpl implements ProductDao {

    private static final ProductDao productDao = new ProductDaoImpl();

    private static final Map<Long, Product> productMap = new TreeMap<>();

    static {
        productMap.put(1L,new Product(1,"Морковь", 5, 500));
        productMap.put(2L,new Product(2,"Помидор", 20, 1000));
        productMap.put(3L,new Product(3,"Картофель", 15, 2000));
        productMap.put(4L,new Product(4,"Буряк", 10, 800));
        productMap.put(5L,new Product(5,"Чеснок", 12, 5000));
        productMap.put(6L,new Product(6,"Помидор черный", 30, 500));
    }

    private ProductDaoImpl() {
    }

    public static ProductDao getInstance() {
        return productDao;
    }

    @Override
    public void save(Product product) {
        productMap.put(product.getId(), product);
    }

    @Override
    public void update(Product product) {
        productMap.put(product.getId(), product);
    }

    @Override
    public void delete(Product product) {
        productMap.remove(product.getId());
    }

    @Override
    public List<Product> findByName(String name) {
        return productMap.values().stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(long id) {
        return productMap.get(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }
}