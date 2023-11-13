package ecommerce.donatto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecommerce.donatto.model.OrderDetail;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
    
}
