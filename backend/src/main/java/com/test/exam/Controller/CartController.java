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
    @GetMapping("/cart")
    public ResponseEntity<CartDTO> getCart(){
        return getCartService.execute(null);
    }

    //Add to Cart
    @PostMapping("/cart/{productId}")
    public ResponseEntity<CartItemDTO> addToCart(@PathVariable Integer productId){
        return addToCartService.execute(productId);
    }

    //Update Cart
    @PutMapping("/cart/{productId}")
    public ResponseEntity<CartItemDTO> updateCart(@PathVariable Integer productId, @RequestBody UpdateCartCommand command){
        command.setProductId(productId);
        return updateToCartService.execute(command);
    }

    //Delete from Cart
    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<Void> deleteFromCart(@PathVariable Integer productId){
        return deleteCartService.execute(productId);
    }

    //Get Cart Total 
    @GetMapping("/cart/summary")
    public ResponseEntity<CartSummaryDTO> getCartTotal(){
        return getCartTotalService.execute(null);
    }

    //Clear Cart 
    @DeleteMapping("/cart/clear")
    public ResponseEntity<Void> clearCart(){
        return clearCartService.execute(null);
    }
    
}
