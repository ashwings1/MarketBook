package com.test.exam.Service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ProductNotFoundException;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductRepository;

@Service
public class DeleteProductService implements Command<Integer, Void> {
    private final ProductRepository productRepository;

    public DeleteProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Integer id){
        //If product present, delete it 
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        //Throw exception
        throw new ProductNotFoundException();
    }
}
