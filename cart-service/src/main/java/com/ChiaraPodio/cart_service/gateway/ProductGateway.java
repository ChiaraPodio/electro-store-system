package com.ChiaraPodio.cart_service.gateway;

import com.ChiaraPodio.cart_service.repository.IProductsAPI;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductGateway {

    private final IProductsAPI apiProducts;

    public ProductGateway(IProductsAPI apiProducts) {
        this.apiProducts = apiProducts;
    }

    @Retry(name = "product-retry")
    public void addStock(Long productId, Integer quantity) {
        apiProducts.addStock(productId, quantity);
    }

    @Retry(name = "product-retry")
    public void removeStock(Long productId, Integer quantity) {
        apiProducts.removeStock(productId, quantity);
    }
}
