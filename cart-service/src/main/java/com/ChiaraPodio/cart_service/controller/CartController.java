package com.ChiaraPodio.cart_service.controller;

import com.ChiaraPodio.cart_service.dto.CartDTO;
import com.ChiaraPodio.cart_service.dto.CartResponseDTO;
import com.ChiaraPodio.cart_service.model.Cart;
import com.ChiaraPodio.cart_service.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartServ;

    @PostMapping("/save")
    public CartResponseDTO saveCart (@RequestBody CartDTO cartDTO) {
         return cartServ.createCart(cartDTO);
    }

    @GetMapping("/{cart_id}")
    public Cart findCart (@PathVariable Long cart_id) {
        return cartServ.findCart(cart_id);
    }

    @GetMapping("/all")
    public List<Cart> getCarts() {
        return cartServ.getCarts();
    }

    @PutMapping("/edit/{cart_id}")
    public CartResponseDTO editCart(@PathVariable Long cart_id,
                         @RequestBody CartDTO cartDTO) {
        return cartServ.editCart(cart_id, cartDTO);
    }

    @DeleteMapping("/delete/{cart_id}")
    public void deleteCart(@PathVariable Long cart_id) {
        cartServ.deleteCart(cart_id);
    }

}
