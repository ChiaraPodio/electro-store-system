package com.ChiaraPodio.products_service.service;

import com.ChiaraPodio.products_service.model.Product;
import java.util.List;

import java.util.List;

public interface IProductService {

    public void saveProduct(Product product);

    public Product findProduct(Long product_id);

    public List<Product> getProducts();

    public Product editProduct(Long product_id, String newName, String newBrand, Double newCurrent_price, Integer newStock);

    public void deleteProduct(Long product_id);

    public void removeStock (Long product_id,Integer product_quantity);

    public void addStock (Long product_id,Integer addit_product_quantity);

}
