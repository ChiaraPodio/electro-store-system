package com.ChiaraPodio.products_service.controller;

import com.ChiaraPodio.products_service.dto.ProductRequestDto;
import com.ChiaraPodio.products_service.model.Product;
import com.ChiaraPodio.products_service.service.IProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final IProductService productServ;

    public ProductController(IProductService productServ) {
        this.productServ = productServ;
    }

    @PostMapping("/save")
    public void createProduct (@RequestBody ProductRequestDto productRequestDto) {
        productServ.createProduct(productRequestDto);
    }

    @GetMapping("/{product_id}")
    public Product findProduct (@PathVariable Long product_id) {
        return productServ.findProduct(product_id);
    }

    @GetMapping("/all")
    public List<Product> getProducts() {
        return productServ.getProducts();
    }

    @PatchMapping("/{id}")
    public Product editProductPartially(
            @PathVariable Long id,
            @RequestBody ProductRequestDto productRequestDto) {

        return productServ.editProduct(id, productRequestDto);
    }

    @DeleteMapping("/delete/{product_id}")
    public void deleteProduct(@PathVariable Long product_id) {
        productServ.deleteProduct(product_id);
    }

    @PutMapping("/remove/stock/{product_id}/{product_quantity}")
    public void removeStock (@PathVariable Long product_id,
                             @PathVariable Integer product_quantity) {
        productServ.removeStock(product_id, product_quantity);
    }

    @PutMapping("/add/stock/{product_id}/{addit_product_quantity}")
    public void addStock (@PathVariable Long product_id,
                          @PathVariable Integer addit_product_quantity) {
        productServ.addStock(product_id, addit_product_quantity);
    }

}
