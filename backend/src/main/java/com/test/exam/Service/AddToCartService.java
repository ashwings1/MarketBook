package com.test.exam.Service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Model.Cart;
import com.test.exam.Model.CartItem;
import com.test.exam.Model.CartItemDTO;
import com.test.exam.Model.CartItemRepository;
import com.test.exam.Model.CartRepository;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductRepository;

@Service
public class AddToCartService implements Command<Integer, CartItemDTO>{
    
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserContextService userContextService;

    public AddToCartService(CartItemRepository cartItemRepository, 
                           CartRepository cartRepository, 
                           ProductRepository productRepository,
                           UserContextService userContextService){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userContextService = userContextService;
    }

    @Override
    public ResponseEntity<CartItemDTO> execute(Integer productId){

        Integer userId = userContextService.getCurrentUserId();
        
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(java.time.LocalDateTime.now());
            cart.setUpdatedAt(java.time.LocalDateTime.now());
            cart = cartRepository.save(cart);
        }
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existingItem.isPresent()){
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1); 
            CartItem savedCartItem = cartItemRepository.save(item);
            
            CartItemDTO cartItemDTO = new CartItemDTO(savedCartItem);
            return ResponseEntity.ok(cartItemDTO);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(productId);
            cartItem.setQuantity(1); 

            CartItem savedCartItem = cartItemRepository.save(cartItem);
            
            CartItemDTO cartItemDTO = new CartItemDTO(savedCartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDTO);
        }
    }
}
