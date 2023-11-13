package ecommerce.donatto.service;

import java.util.List;
import java.util.Optional;

import ecommerce.donatto.model.Product;

public interface ProductService {
    public Product save( Product product);
    public Optional<Product> get(Integer id);
    public void update(Product product);
    public void delete(Integer id);
    public List<Product> findAll();

}
