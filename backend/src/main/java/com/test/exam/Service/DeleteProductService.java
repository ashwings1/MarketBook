package com.test.exam.Service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ProductNotFoundException;
import com.test.exam.Exception.ProductNotOwnedException;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductRepository;


@Service
public class DeleteProductService implements Command<Integer, Void> {
    private final ProductRepository productRepository;
    private final UserContextService userContextService;

    public DeleteProductService(ProductRepository productRepository, UserContextService userContextService){
        this.productRepository = productRepository;
        this.userContextService = userContextService;
    }

    @Override
    public ResponseEntity<Void> execute(Integer id){
        Integer sellerId = userContextService.getCurrentUserId();

        Optional<Product> productOptional = productRepository.findBySellerIdAndId(sellerId, id);

        if (productOptional.isEmpty()){
            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isPresent()){
                throw new ProductNotOwnedException("Product with ID " + id + " does not belong to you");
            } else {
                throw new ProductNotFoundException();
            }
        }

        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        /* 
        //If product present, delete it 
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        //Throw exception
        throw new ProductNotFoundException();
        */
    }
}
