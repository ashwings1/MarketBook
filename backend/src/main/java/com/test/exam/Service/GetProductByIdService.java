package com.test.exam.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Query;
import com.test.exam.Exception.ResourceNotFoundException;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductDTO;
import com.test.exam.Model.ProductRepository;

@Service
public class GetProductByIdService implements Query<Integer, ProductDTO> {
    
    private final ProductRepository productRepository;

    public GetProductByIdService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute (Integer id){
        Product productDetail = productRepository.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        ProductDTO productDTO = new ProductDTO(productDetail);

        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }
}
