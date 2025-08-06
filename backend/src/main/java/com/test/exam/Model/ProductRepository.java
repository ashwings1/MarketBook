package com.test.exam.Model;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> getProductByName(String name);
    
    //Get product by seller (index)
    List<Product> findBySellerIdOrderByCreatedAtDesc(Integer sellerId);

    //Check if product belongs to seller
    //@Query("SELECT p FROM examproduct p WHERE p.sellerId = :sellerId AND p.id = :id")
    //Optional<Product> findBySellerIdAndId(@Param("sellerId") Integer sellerId, @Param("id") Integer id);

    //Get products with seller details
    //@Query("SELECT p FROM examproduct p JOIN p.seller s where p.sellerId = :sellerId ORDER BY p.createdAt DESC")
    //List<Product> findBySellerIdWithSellerDetails(@Param("sellerId") Integer sellerId);

    //Count products by seller
    Integer countBySellerId(Integer sellerId);
    
    //Find products by category and seller
    List<Product> findBySellerIdAndCategoryOrderByCreatedAtDesc(Integer sellerId, String category);

    //Public queries
    //@Query("SELECT p FROM examproduct p JOIN p.seller s WHERE s.role = 'SELLER' ORDER BY p.createdAt DESC")
    //List<Product> findAllActiveProducts();

    
}
