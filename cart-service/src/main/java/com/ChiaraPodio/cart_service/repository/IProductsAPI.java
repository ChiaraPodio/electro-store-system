package com.ChiaraPodio.cart_service.repository;

import com.ChiaraPodio.cart_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name="products-service")
public interface IProductsAPI {

    @GetMapping("/product/{product_id}")
    public ProductDTO getProductById (@PathVariable("product_id") Long product_id);

    @PutMapping("/product/remove/stock/{product_id}/{product_quantity}")
    public void removeStock (@PathVariable("product_id") Long product_id,
                             @PathVariable("product_quantity") Integer product_quantity);

    @PutMapping("/product/add/stock/{product_id}/{addit_product_quantity}")
    public void addStock (@PathVariable("product_id") Long product_id,
                             @PathVariable("addit_product_quantity") Integer addit_product_quantity);


}
