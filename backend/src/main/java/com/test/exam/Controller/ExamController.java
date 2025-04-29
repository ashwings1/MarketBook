package com.test.exam.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.test.exam.Model.Product;
import com.test.exam.Model.ProductDTO;
import com.test.exam.Model.UpdateProductCommand;
import com.test.exam.Service.CreateProductService;
import com.test.exam.Service.DeleteProductService;
import com.test.exam.Service.GetProductService;
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

    public ExamController(GetProductService getProductService, CreateProductService createProductService, UpdateProductService updateProductService, DeleteProductService deleteProductService){
        this.getProductService = getProductService;
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
        this.deleteProductService = deleteProductService;
    }
    
    //Get request
    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getProduct(){
        return getProductService.execute(null);
    }

    //Post Request
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product){
        return createProductService.execute(product);
    }

    //Put Request
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody Product product){
        return updateProductService.execute(new UpdateProductCommand(id, product));
    }

    //Delete Request
    @DeleteMapping("/product/{id}")
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
