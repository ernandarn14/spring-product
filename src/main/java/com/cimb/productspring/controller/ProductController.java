package com.cimb.productspring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cimb.productspring.dao.ProductRepo;
import com.cimb.productspring.entity.Product;
import com.cimb.productspring.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired 
	ProductService productService;
	
	@GetMapping
	public Iterable<Product> getProducts(){
		return productService.getProducts();
	}
	
	@GetMapping("/{productId}")
	public Product getProductById(@PathVariable int productId) {
		return productService.getProductById(productId);
	}
	
	
	@PostMapping
	public Product addProduct(@RequestParam("file") MultipartFile file, @RequestParam("productData") String productString) throws JsonMappingException, JsonProcessingException {
		Date date = new Date();
		
		Product product = new ObjectMapper().readValue(productString, Product.class);
		
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "PRODUCT-" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
	
		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/download/")
				.path(fileName).toUriString();
		
		product.setImage(fileDownloadUri);
		
		product.setId(0);
		return productRepo.save(product);
	}
	
	@PutMapping("/{productId}")
	public Product editProduct(@RequestParam("file") MultipartFile file, @RequestParam("productData") String productString, @PathVariable int productId) throws JsonMappingException, JsonProcessingException {
		Date date = new Date();
		
		Product product = new ObjectMapper().readValue(productString, Product.class);
		
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "PRODUCT-" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
	
		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/download/")
				.path(fileName).toUriString();
		
		product.setImage(fileDownloadUri);
		
		return productRepo.save(product);
	}
	
	@DeleteMapping("/{productId}")
	public void deleteProduct(@PathVariable int productId) {
		 productService.deleteProduct(productId);
	}

}
