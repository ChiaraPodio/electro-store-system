package com.ChiaraPodio.products_service.service;

import com.ChiaraPodio.products_service.dto.ProductRequestDto;
import com.ChiaraPodio.products_service.model.Product;
import com.ChiaraPodio.products_service.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepo;

    public ProductService(IProductRepository productRepo) {
        this.productRepo = productRepo;
    }

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
    public Product editProduct(Long product_id, ProductRequestDto productRequestDto) {

        Product product = findProduct(product_id);

        if (productRequestDto.getName() != null) {
            product.setName(productRequestDto.getName());
        }

        if (productRequestDto.getBrand() != null) {
            product.setBrand(productRequestDto.getBrand());
        }

        if (productRequestDto.getCurrent_price() != null) {
            product.setCurrent_price(productRequestDto.getCurrent_price());
        }

        if (productRequestDto.getStock() != null) {
            product.setStock(productRequestDto.getStock());
        }

        saveProduct(product);

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

    @Override
    public void createProduct (ProductRequestDto productRequestDto) {
        Product product = new Product();

        product.setName(productRequestDto.getName());
        product.setBrand(productRequestDto.getBrand());
        product.setCurrent_price(productRequestDto.getCurrent_price());
        product.setStock(productRequestDto.getStock());

        this.saveProduct(product);
    }



}
