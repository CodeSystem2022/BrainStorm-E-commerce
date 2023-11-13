package ecommerce.donatto.service;

import java.util.List;
import java.util.Optional;

import ecommerce.donatto.model.User;

public interface IUserService {
    List<User> findAll();
    Optional<User> findById(Integer id);
    User save (User user);
    Optional<User> findByUsername(String username);
}
