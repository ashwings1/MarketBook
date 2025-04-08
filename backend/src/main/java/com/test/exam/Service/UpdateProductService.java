package com.test.exam.Service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ProductNotFoundException;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductDTO;
import com.test.exam.Model.ProductRepository;
import com.test.exam.Model.UpdateProductCommand;

@Service
public class UpdateProductService implements Command<UpdateProductCommand, ProductDTO> {
    
    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command){

        //If in repository -> update 
        Optional<Product> productOptional = productRepository.findById(command.getId());
        if (productOptional.isPresent()){
            Product product = command.getProduct();
            product.setId(command.getId());

            return ResponseEntity.ok(new ProductDTO(product));
        }
        //Throw exception if not in repository
        throw new ProductNotFoundException();
    }
}
