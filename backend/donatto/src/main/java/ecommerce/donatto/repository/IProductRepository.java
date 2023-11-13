package ecommerce.donatto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecommerce.donatto.model.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer>{
    
}
