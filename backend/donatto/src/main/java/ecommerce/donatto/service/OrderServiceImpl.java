package ecommerce.donatto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.donatto.model.Order;
import ecommerce.donatto.model.User;
import ecommerce.donatto.repository.IOrderRepository;

@Service
public class OrderServiceImpl implements IOrderService{

    @Autowired //Para que spring sepa que tiene que inyectar el objeto a esta clase de ser
    private IOrderRepository orderRepository;
    
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    //Pasar el numero de las ordenes a string con el objetivo de hacer mas sencillo el incremento?
    //Format 000001
    public String generateOrderNumber() {
        int number=0;
        String numberConc="";

        List<Order> orders = findAll();

        List<Integer> numbers = new ArrayList<Integer>();

        orders.stream().forEach(o -> numbers.add(Integer.parseInt(o.getNumber())));

        if (orders.isEmpty()) {
            number=1;
        } else {
            number=numbers.stream().max(Integer::compare).get();
            number++;
        }
        if (number<10) {
            numberConc="0000000"+String.valueOf(number);
        } else if(number<100) { //se reemplaza un cero con el n de orden
            numberConc="000000"+String.valueOf(number);
        } else if(number<1000) {
            numberConc="00000"+String.valueOf(number);
        }

        return numberConc;
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }
    
}
