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
        productMap.put(1L, new Product(1, "apple", 5, 500));
        productMap.put(2L, new Product(2, "apricot ", 20, 1000));
        productMap.put(3L, new Product(3, "avocado", 15, 2000));
        productMap.put(4L, new Product(4, "pineapple", 10, 800));
        productMap.put(5L, new Product(5, "banana", 12, 5000));
        productMap.put(6L, new Product(6, "bergamot", 40, 55));
        productMap.put(7L, new Product(7, "durian", 11, 500));
        productMap.put(8L, new Product(8, "grapefruit", 42, 500));
        productMap.put(9L, new Product(9, "kiwi", 12, 71));
        productMap.put(10L, new Product(10, "lime", 44, 14));
        productMap.put(11L, new Product(11, "lemon", 74, 14));
        productMap.put(12L, new Product(12, "loquat", 33, 14));
        productMap.put(13L, new Product(13, "mango", 100, 16));
        productMap.put(14L, new Product(14, "melon", 44, 454));
        productMap.put(15L, new Product(15, "nectarine", 50, 11));
        productMap.put(16L, new Product(16, "orange", 11, 441));
        productMap.put(17L, new Product(17, "passion fruit", 25, 6));
        productMap.put(18L, new Product(18, "papaya", 22, 45));
        productMap.put(19L, new Product(19, "peach", 42, 240));
        productMap.put(20L, new Product(20, "pear", 44, 100));
        productMap.put(21L, new Product(21, "persimmon", 12, 787));
        productMap.put(22L, new Product(22, "pineapple", 21, 54));
        productMap.put(23L, new Product(23, "plum", 37, 457));
        productMap.put(24L, new Product(24, "pomegranate", 44, 222));
        productMap.put(25L, new Product(25, "pomelo", 150, 11));
        productMap.put(26L, new Product(26, "tangerine", 80, 10));
        productMap.put(27L, new Product(27, "quince", 159, 1000));
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