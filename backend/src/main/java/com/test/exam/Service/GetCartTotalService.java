package com.test.exam.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Model.Cart;
import com.test.exam.Model.CartItem;
import com.test.exam.Model.CartItemRepository;
import com.test.exam.Model.CartRepository;
import com.test.exam.Model.CartSummaryDTO;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductRepository;

@Service
public class GetCartTotalService implements Command<Integer, CartSummaryDTO>{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public GetCartTotalService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<CartSummaryDTO> execute(Integer userId){
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null){
            return ResponseEntity.ok(new CartSummaryDTO(0, 0.0, 0));
        }

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        double totalAmount = 0.0;
        int totalItems = 0;

        for (CartItem item: cartItems){
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            totalAmount += product.getPrice() * item.getQuantity();
            totalItems += item.getQuantity();
        }

        int uniqueItems = cartItems.size();
        
        return ResponseEntity.ok(new CartSummaryDTO(totalItems, totalAmount, uniqueItems));
    }
    
}
