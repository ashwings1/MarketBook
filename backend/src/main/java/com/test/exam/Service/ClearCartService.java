package com.test.exam.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.exam.Command;
import com.test.exam.Model.Cart;
import com.test.exam.Model.CartItemRepository;
import com.test.exam.Model.CartRepository;

@Service
public class ClearCartService implements Command<Integer, Void> {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserContextService userContextService;

    public ClearCartService(CartRepository cartRepository, CartItemRepository cartItemRepository, UserContextService userContextService){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userContextService = userContextService;
    }

    @Override
    @Transactional
    public ResponseEntity<Void> execute(Integer userId){
        Integer currentUserId = userContextService.getCurrentUserId();

        Cart cart = cartRepository.findByUserId(currentUserId);

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        cartItemRepository.deleteByCartId(cart.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
