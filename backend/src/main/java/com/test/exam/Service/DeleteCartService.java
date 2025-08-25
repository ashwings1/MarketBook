package com.test.exam.Service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ResourceNotFoundException;
import com.test.exam.Model.Cart;
import com.test.exam.Model.CartItem;
import com.test.exam.Model.CartItemRepository;
import com.test.exam.Model.CartRepository;

@Service
public class DeleteCartService implements Command<Integer, Void> {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserContextService userContextService;

    public DeleteCartService(CartItemRepository cartItemRepository, CartRepository cartRepository, UserContextService userContextService){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.userContextService = userContextService;
    }

    @Override
    public ResponseEntity<Void> execute(Integer cartItemId){
        Integer userId = userContextService.getCurrentUserId();

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for user");
        }

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
