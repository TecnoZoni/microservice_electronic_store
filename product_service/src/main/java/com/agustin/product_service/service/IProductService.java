package com.agustin.product_service.service;

import com.agustin.product_service.model.Product;
import java.util.List;

public interface IProductService {

    public Product getProduct(Long id);

    public List<Product> getProducts();

    public Product saveProduct(Product product);

    public Product editProduct(Product product);
    
    public void deleteProduct(Long id);
}
