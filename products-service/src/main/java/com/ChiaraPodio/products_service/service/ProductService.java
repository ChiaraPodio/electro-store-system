package com.ChiaraPodio.products_service.service;

import com.ChiaraPodio.products_service.model.Product;
import com.ChiaraPodio.products_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepo;

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public Product findProduct(Long product_id) {
        Product product = productRepo.findById(product_id).orElse(null);
        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> productList = productRepo.findAll();
        return productList;
    }

    @Override
    public Product editProduct(Long product_id, String newName, String newBrand, Double newCurrent_price, Integer newStock) {
        Product product = this.findProduct(product_id);

        if (newName != null) {
            product.setName(newName);}
        if (newBrand != null) {
            product.setBrand(newBrand);}
        if (newCurrent_price != null) {
            product.setCurrent_price(newCurrent_price);}
        if (newStock != null) {
            product.setStock(newStock);}

        this.saveProduct(product);

        return product;
    }

    @Override
    public void deleteProduct(Long product_id) {
        productRepo.deleteById(product_id);
    }

    @Override
    public void removeStock (Long product_id,Integer product_quantity) {
        Product product = this.findProduct(product_id);
        product.setStock(product.getStock()-product_quantity);
        this.saveProduct(product);
    }

    @Override
    public void addStock (Long product_id,Integer addit_product_quantity) {
        Product product = this.findProduct(product_id);
        product.setStock(product.getStock() + addit_product_quantity);
        this.saveProduct(product);
    }



}
