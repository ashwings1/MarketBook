package com.test.exam.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductDTO;
import com.test.exam.Model.ProductRepository;

@Service
public class CreateProductService implements Command<Product, ProductDTO>{
    
    private final ProductRepository productRepository;
    private final UserContextService userContextService;

    public CreateProductService(ProductRepository productRepository, UserContextService userContextService){
        this.productRepository = productRepository;
        this.userContextService = userContextService;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Product product){
        //Current seller ID from auth context
        Integer sellerId = userContextService.getCurrentUserId();

        product.setSellerId(sellerId);
        
        Product createdProduct = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(createdProduct));
    }
}
