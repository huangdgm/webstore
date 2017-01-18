package com.packt.webstore.domain.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface ProductRepository {
	
	List<Product> getAllProducts();
	
	Product getProductById(String productId);
	
	List<Product> getProductsByCategory(String category);

	List<Product> getProductsByManufacturer(String manufacturer);
	
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	
	Set<Product> getProductsByPriceRange(Map<String, List<String>> filterParams);
	
	void addProduct(Product product);
}