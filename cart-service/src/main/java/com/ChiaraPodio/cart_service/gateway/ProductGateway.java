package com.ChiaraPodio.cart_service.gateway;

import com.ChiaraPodio.cart_service.dto.ProductDTO;
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

    @Retry(name = "products-service")
    public ProductDTO getProductById(Long productId) {
        return apiProducts.getProductById(productId);
    }

    @Retry(name = "products-service")
    public void addStock(Long productId, Integer quantity) {
        apiProducts.addStock(productId, quantity);
    }

    @Retry(name = "products-service")
    public void removeStock(Long productId, Integer quantity) {
        apiProducts.removeStock(productId, quantity);
    }

    // evito que la base de datos quede inconsistente si la comunicación con la api falla en una
    // iteración diferente a la primera
}
