package com.ChiaraPodio.products_service.service;

import com.ChiaraPodio.products_service.dto.ProductRequestDto;
import com.ChiaraPodio.products_service.model.Product;
import com.ChiaraPodio.products_service.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private IProductRepository productRepo;

    @InjectMocks
    private ProductService productService;

    @Test
    public void saveProductTest() {
        Product product = new Product(null, "Smart TV 55 Inch 4K", "Samsung", 899.99, 15);

        productService.saveProduct(product);

        verify(productRepo).save(product);
        verifyNoMoreInteractions(productRepo);
    }

    @Test
    public void findProductTest() {

        Product product = new Product(1L, "Smart TV 55 Inch 4K", "Samsung", 899.99, 15);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findProduct(1L);

        assertNotNull(result);
        assertEquals(product, result);

        verify(productRepo).findById(1L);
        verifyNoMoreInteractions(productRepo);
    }

    @Test
    public void findProductNotFoundTest() {

        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.findProduct(1L);

        assertNull(result);

        verify(productRepo).findById(1L);
        verifyNoMoreInteractions(productRepo);
    }

    @Test
    public void getProductsTest() {

        List<Product> products = List.of(
                new Product(1L, "TV", "Samsung", 900.0, 10),
                new Product(2L, "Notebook", "Lenovo", 1200.0, 5)
        );

        when(productRepo.findAll()).thenReturn(products);

        List<Product> result = productService.getProducts();

        assertEquals(2, result.size());
        assertEquals(products, result);

        verify(productRepo).findAll();
        verifyNoMoreInteractions(productRepo);
    }

    @Test
    public void editProduct_OnlyPriceTest() {

        Product product = new Product(1L, "TV", "Samsung", 900.0, 10);

        ProductRequestDto dto = new ProductRequestDto();
        dto.setCurrent_price(1200.0);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.editProduct(1L, dto);

        assertEquals("Samsung", result.getBrand()); // no cambió
        assertEquals(1200.0, result.getCurrent_price()); // cambió
        assertEquals(10, result.getStock()); // no cambió

        verify(productRepo).findById(1L);
        verify(productRepo).save(product);
    }

    @Test
    public void deleteProductTest() {

        productService.deleteProduct(1L);

        verify(productRepo).deleteById(1L);
        verifyNoMoreInteractions(productRepo);
    }

    @Test
    public void removeStockTest() {

        Product product = new Product(1L, "TV", "Samsung", 900.0, 15);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        productService.removeStock(1L, 5);

        assertEquals(10, product.getStock());

        verify(productRepo).findById(1L);
        verify(productRepo).save(product);
        verifyNoMoreInteractions(productRepo);
    }

    @Test
    public void addStockTest() {

        Product product = new Product(1L, "TV", "Samsung", 900.0, 15);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        productService.addStock(1L, 5);

        assertEquals(20, product.getStock());

        verify(productRepo).findById(1L);
        verify(productRepo).save(product);
        verifyNoMoreInteractions(productRepo);
    }

    @Test
    public void createProductTest() {

        ProductRequestDto dto = new ProductRequestDto("TV","Samsung", 900.0, 15);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        productService.createProduct(dto);

        verify(productRepo).save(captor.capture());

        Product savedProduct = captor.getValue();

        assertEquals("TV", savedProduct.getName());
        assertEquals("Samsung", savedProduct.getBrand());
        assertEquals(900.0, savedProduct.getCurrent_price());
        assertEquals(15, savedProduct.getStock());

        verifyNoMoreInteractions(productRepo);
    }

}
