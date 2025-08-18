package com.test.exam.Model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    Optional<CartItem> findByCartIdAndProductId(Integer cartId, Integer productId);

    List<CartItem> findByCartId(Integer cartId);
}
