package com.test.exam.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.exam.Model.CartDTO;
import com.test.exam.Model.CartItemDTO;
import com.test.exam.Model.CartSummaryDTO;
import com.test.exam.Model.UpdateCartCommand;
import com.test.exam.Service.AddToCartService;
import com.test.exam.Service.ClearCartService;
import com.test.exam.Service.DeleteCartService;
import com.test.exam.Service.GetCartService;
import com.test.exam.Service.GetCartTotalService;
import com.test.exam.Service.UpdateToCartService;

@RestController
public class CartController {
    
    private final GetCartService getCartService;
    private final AddToCartService addToCartService;
    private final UpdateToCartService updateToCartService;
    private final DeleteCartService deleteCartService;
    private final GetCartTotalService getCartTotalService;
    private final ClearCartService clearCartService;

    public CartController(GetCartService getCartService, AddToCartService addToCartService, UpdateToCartService updateToCartService, DeleteCartService deleteCartService, GetCartTotalService getCartTotalService, ClearCartService clearCartService){
        this.getCartService = getCartService;
        this.addToCartService = addToCartService;
        this.updateToCartService = updateToCartService;
        this.deleteCartService = deleteCartService;
        this.getCartTotalService = getCartTotalService;
        this.clearCartService = clearCartService;
    }

    //Get Cart
    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Integer userId){
        return getCartService.execute(userId);
    }

    //Add to Cart
    @PostMapping("/cart/{productId}")
    public ResponseEntity<CartItemDTO> addToCart(@PathVariable Integer productId){
        return addToCartService.execute(productId);
    }

    //Update Cart
    @PutMapping("/cart/item/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateCart(@PathVariable Integer cartItemId, @RequestBody UpdateCartCommand command){
        command.setCartItemId(cartItemId);
        return updateToCartService.execute(command);
    }

    //Delete from Cart
    @DeleteMapping("/cart/item/{cartItemId}")
    public ResponseEntity<Void> deleteFromCart(@PathVariable Integer cartItemId){
        return deleteCartService.execute(cartItemId);
    }

    //Get Cart Total
    @GetMapping("/cart/{userId}/summary")
    public ResponseEntity<CartSummaryDTO> getCartTotal(@PathVariable Integer userId){
        return getCartTotalService.execute(userId);
    }

    //Clear Cart
    @DeleteMapping("/cart/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Integer userId){
        return clearCartService.execute(userId);
    }
    
}
