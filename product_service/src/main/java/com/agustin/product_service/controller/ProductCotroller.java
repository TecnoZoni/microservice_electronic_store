package com.agustin.product_service.controller;

import com.agustin.product_service.model.Product;
import com.agustin.product_service.service.IProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductCotroller {

    private final IProductService prodService;

    public ProductCotroller(IProductService prodService) {
        this.prodService = prodService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(prodService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(prodService.getProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return new ResponseEntity<>(prodService.saveProduct(product), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Product> editProduct(@RequestBody Product product) {
        return new ResponseEntity<>(prodService.editProduct(product), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        prodService.deleteProduct(id);
        return new ResponseEntity<>("El Producto se elimino con exito.", HttpStatus.OK);
    }

}
