package ecommerce.donatto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecommerce.donatto.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    //Seleccionamos el campo por el cual quiero buscar al user
    Optional<User> findByUsername(String username);
    //Al imponer este metodo se busca el usuario que coincida
}
