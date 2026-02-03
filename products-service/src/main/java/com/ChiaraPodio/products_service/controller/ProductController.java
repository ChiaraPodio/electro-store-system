package com.ChiaraPodio.products_service.controller;

import com.ChiaraPodio.products_service.model.Product;
import com.ChiaraPodio.products_service.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productServ;

    @PostMapping("/save")
    public void saveProduct (@RequestBody Product product) {
        productServ.saveProduct(product);
    }

    @GetMapping("/{product_id}")
    public Product findProduct (@PathVariable Long product_id) {
        return productServ.findProduct(product_id);
    }

    @GetMapping("/all")
    public List<Product> getProducts() {
        return productServ.getProducts();
    }

    @PutMapping("/edit/{product_id}")
    public Product editProduct(@PathVariable Long product_id,
                                 @RequestParam (required = false) String newName,
                                 @RequestParam (required = false) String newBrand,
                                 @RequestParam (required = false) Double newCurrent_price,
                                 @RequestParam (required = false) Integer newStock) {
        return productServ.editProduct(product_id, newName, newBrand, newCurrent_price, newStock);
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
