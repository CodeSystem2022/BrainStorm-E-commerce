package ecommerce.donatto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecommerce.donatto.model.Order;
import ecommerce.donatto.model.User;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer>{
    //Creamos un metodo para obtener las ordenes del usuario
    List<Order> findByUser (User user);
}
