package com.test.exam.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ResourceNotFoundException;
import com.test.exam.Exception.UnauthorizedException;
import com.test.exam.Model.Cart;
import com.test.exam.Model.CartItem;
import com.test.exam.Model.CartItemDTO;
import com.test.exam.Model.CartItemRepository;
import com.test.exam.Model.CartRepository;
import com.test.exam.Model.UpdateCartCommand;

@Service
public class UpdateToCartService implements Command<UpdateCartCommand, CartItemDTO> {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserContextService userContextService;

    public UpdateToCartService(CartRepository cartRepository, CartItemRepository cartItemRepository,UserContextService userContextService){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userContextService = userContextService;
    }

    @Override
    public ResponseEntity<CartItemDTO> execute(UpdateCartCommand command){
        Integer userId = userContextService.getCurrentUserId();

        CartItem cartItem = cartItemRepository.findById(command.getCartItemId()).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || !cart.getId().equals(cartItem.getCartId())){
            throw new UnauthorizedException("Cart item does not belong to you");
        }

        cartItem.setQuantity(command.getQuantity());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return ResponseEntity.ok(new CartItemDTO(savedCartItem));
    }
    
}
