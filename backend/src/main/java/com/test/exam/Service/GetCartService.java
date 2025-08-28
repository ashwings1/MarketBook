package com.test.exam.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Model.Cart;
import com.test.exam.Model.CartDTO;
import com.test.exam.Model.CartItem;
import com.test.exam.Model.CartItemDTO;
import com.test.exam.Model.CartItemRepository;
import com.test.exam.Model.CartRepository;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCartService implements Command<Integer, CartDTO> {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserContextService userContextService;

    public GetCartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserContextService userContextService){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userContextService = userContextService;
    }
    
    @Override
    public ResponseEntity<CartDTO> execute(Integer userId){
        userId = userContextService.getCurrentUserId();
        
        Cart cartRepo = cartRepository.findByUserId(userId);
        
        if (cartRepo == null) {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(java.time.LocalDateTime.now());
            cart.setUpdatedAt(java.time.LocalDateTime.now());
            cartRepo = cartRepository.save(cart);
        }
        
        CartDTO cartDTO = new CartDTO(cartRepo);
        List<CartItem> items = cartItemRepository.findByCartId(cartRepo.getId());
        List<CartItemDTO> itemDTOs = items.stream().map(item -> {
            CartItemDTO dto = new CartItemDTO(item);
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null){
                dto.setProductName(product.getName());
                dto.setProductPrice(product.getPrice());
            }
            return dto;
        }).collect(Collectors.toList());
        cartDTO.setItems(itemDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
    }
}
