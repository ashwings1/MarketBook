package com.test.exam.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.UnauthorizedException;
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
    public ResponseEntity<Void> execute(Integer userId){
        Integer currentUserId = userContextService.getCurrentUserId();

        if (!currentUserId.equals(userId)) {
            throw new UnauthorizedException("You can only clear your own cart");
        }

        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        cartItemRepository.deleteByCartId(cart.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
