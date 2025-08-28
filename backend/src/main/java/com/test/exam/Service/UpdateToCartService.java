package com.test.exam.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ResourceNotFoundException;
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

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for user");
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), command.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

        cartItem.setQuantity(command.getQuantity());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return ResponseEntity.ok(new CartItemDTO(savedCartItem));
    }
    
}
