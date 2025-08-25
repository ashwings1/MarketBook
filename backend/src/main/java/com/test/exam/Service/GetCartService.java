package com.test.exam.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Model.Cart;
import com.test.exam.Model.CartDTO;
import com.test.exam.Model.CartRepository;

@Service
public class GetCartService implements Command<Integer, CartDTO> {

    private final CartRepository cartRepository;

    public GetCartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }
    
    @Override
    public ResponseEntity<CartDTO> execute(Integer userId){
        // Returns cart for userId
        Cart cartRepo = cartRepository.findByUserId(userId);
        
        if (cartRepo == null) {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(java.time.LocalDateTime.now());
            cart.setUpdatedAt(java.time.LocalDateTime.now());
            cartRepo = cartRepository.save(cart);
        }
        
        CartDTO cartDTO = new CartDTO(cartRepo);
        return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
    }
}
