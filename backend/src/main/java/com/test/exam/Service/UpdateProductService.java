package com.test.exam.Service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ProductNotFoundException;
import com.test.exam.Exception.ProductNotOwnedException;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductDTO;
import com.test.exam.Model.ProductRepository;
import com.test.exam.Model.UpdateProductCommand;

@Service
public class UpdateProductService implements Command<UpdateProductCommand, ProductDTO> {
    
    private final ProductRepository productRepository;
    private final UserContextService userContextService;

    public UpdateProductService(ProductRepository productRepository, UserContextService userContextService){
        this.productRepository = productRepository;
        this.userContextService = userContextService;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command){
        Integer sellerId = userContextService.getCurrentUserId();

        Optional<Product> productOptional = productRepository.findBySellerIdAndId(sellerId, command.getId());

        if (productOptional.isEmpty()){
            Optional<Product> existingProduct = productRepository.findById(command.getId());
            if (existingProduct.isPresent()){
                throw new ProductNotOwnedException("Product with ID " + command.getId() + " does not belong to you");
            } else {
                throw new ProductNotFoundException();
            }
        }
        
        Product existingProduct = productOptional.get();
        Product updateProduct = command.getProduct();

        existingProduct.setName(updateProduct.getName());
        existingProduct.setDescription(updateProduct.getDescription());
        existingProduct.setPrice(updateProduct.getPrice());
        existingProduct.setCategory(updateProduct.getCategory());
        existingProduct.setImageUrl(updateProduct.getImageUrl());

        Product savedProduct = productRepository.save(existingProduct);

        return ResponseEntity.ok(new ProductDTO(savedProduct));

        /* 
        //If in repository -> update 
        Optional<Product> productOptional = productRepository.findById(command.getId());
        if (productOptional.isPresent()){
            Product product = command.getProduct();
            product.setId(command.getId());

            return ResponseEntity.ok(new ProductDTO(product));
        }
        //Throw exception if not in repository
        throw new ProductNotFoundException();
        */
    }
}
