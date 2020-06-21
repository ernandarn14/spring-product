package com.cimb.productspring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.productspring.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	public Product findByProductName(String productName);
}
