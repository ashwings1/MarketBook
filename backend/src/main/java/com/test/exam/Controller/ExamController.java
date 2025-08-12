package com.test.exam.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.test.exam.Model.Product;
import com.test.exam.Model.ProductDTO;
import com.test.exam.Model.UpdateProductCommand;
import com.test.exam.Service.CreateProductService;
import com.test.exam.Service.DeleteProductService;
import com.test.exam.Service.GetProductByIdService;
import com.test.exam.Service.GetProductService;
import com.test.exam.Service.GetSellerProductService;
import com.test.exam.Service.UpdateProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;


@RestController
public class ExamController {

    private final GetProductService getProductService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;
    private final DeleteProductService deleteProductService;
    private final GetSellerProductService getSellerProductService;
    private final GetProductByIdService getProductByIdService;

    public ExamController(GetProductService getProductService, CreateProductService createProductService, UpdateProductService updateProductService, DeleteProductService deleteProductService, GetSellerProductService getSellerProductService, GetProductByIdService getProductByIdService){
        this.getProductService = getProductService;
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
        this.deleteProductService = deleteProductService;
        this.getSellerProductService = getSellerProductService;
        this.getProductByIdService = getProductByIdService;
    }
    
    //Get products
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProduct(){
        return getProductService.execute(null);
    }

    //Get products by id
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id){
        return getProductByIdService.execute(id);
    }

    //Get products by seller id
    @GetMapping("/products/seller/{sellerId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<ProductDTO>> getSellerProduct(@PathVariable Integer sellerId){
        return getSellerProductService.execute(sellerId);
    }

    //Create product
    @PostMapping("/product/create")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product){
        return createProductService.execute(product);
    }

    //Update Product by id
    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody Product product){
        return updateProductService.execute(new UpdateProductCommand(id, product));
    }

    //Delete Product by id
    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
        return deleteProductService.execute(id);
    }

    Counter frontendClicks = Counter.builder("frontend_clicks").description("Total Frontend clicks").register(Metrics.globalRegistry);
    //React frontend -> when user clicks button 
    @PostMapping("/track-metric")
    public void trackEvent(){
        frontendClicks.increment();
    }
}
