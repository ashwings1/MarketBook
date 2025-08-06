package com.test.exam.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.exam.Query;
import com.test.exam.Model.Product;
import com.test.exam.Model.ProductDTO;
import com.test.exam.Model.ProductRepository;

@Service
public class GetSellerProductService implements Query<Integer, List<ProductDTO>> {
    
    private final ProductRepository productRepository;

    public GetSellerProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(Integer sellerId){
        //Returns list of product by Seller Id
        List<Product> productsBySellerId = productRepository.findBySellerIdOrderByCreatedAtDesc(sellerId);
        List<ProductDTO> productsBySellerIdDTO = productsBySellerId.stream().map(ProductDTO::new).toList();

        return ResponseEntity.status(HttpStatus.OK).body(productsBySellerIdDTO);
    }

}
