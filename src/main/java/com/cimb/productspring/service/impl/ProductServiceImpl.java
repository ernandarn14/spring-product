package com.cimb.productspring.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.productspring.dao.ProductRepo;
import com.cimb.productspring.entity.Product;
import com.cimb.productspring.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepo productRepo;

	@Override
	@Transactional
	public Iterable<Product> getProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	@Override
	@Transactional
	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		Product findProduct = productRepo.findById(productId).get();
		
		if (findProduct == null)
			throw new RuntimeException("Product Not Found");
		
		return findProduct;
	}


	@Override
	@Transactional
	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub
		Product findProduct = productRepo.findById(productId).get();
		
		if (findProduct == null)
			throw new RuntimeException("Product Not Found");
		
		productRepo.deleteById(productId);
		
	}

}
