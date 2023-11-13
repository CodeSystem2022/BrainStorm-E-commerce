package ecommerce.donatto.service;

import java.util.List;
import java.util.Optional;

import ecommerce.donatto.model.Order;
import ecommerce.donatto.model.User;

public interface IOrderService {
    List<Order> findAll();
    Optional<Order> findById(Integer id); 
    Order save (Order order);
    String generateOrderNumber();
    List<Order> findByUser (User user);

}
