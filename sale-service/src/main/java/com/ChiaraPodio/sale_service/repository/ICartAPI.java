package com.ChiaraPodio.sale_service.repository;

import com.ChiaraPodio.sale_service.dto.CartDTO;
import com.ChiaraPodio.sale_service.dto.CartResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="cart-service")
public interface ICartAPI {

    @GetMapping("/cart/save")
    public CartResponseDTO saveCart (@RequestBody CartDTO cartDTO);

    @PutMapping("/cart/edit/{cart_id}")
    public CartResponseDTO editCart(@PathVariable Long cart_id,
                         @RequestBody CartDTO cartDTO);
}
