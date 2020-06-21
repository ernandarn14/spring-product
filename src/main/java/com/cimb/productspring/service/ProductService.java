package com.cimb.productspring.service;

import com.cimb.productspring.entity.Product;

public interface ProductService {
	public Iterable<Product> getProducts();
	public Product getProductById(int productId);
	public void deleteProduct(int productId);
}
