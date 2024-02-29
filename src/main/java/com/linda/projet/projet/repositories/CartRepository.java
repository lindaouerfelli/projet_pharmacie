package com.linda.projet.projet.repositories;


import com.linda.projet.projet.models.Cart;
import com.linda.projet.projet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUser(User user);
    Cart findByUserId(Integer userId);


}
