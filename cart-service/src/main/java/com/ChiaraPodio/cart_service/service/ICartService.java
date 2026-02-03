package com.ChiaraPodio.cart_service.service;

import com.ChiaraPodio.cart_service.dto.CartDTO;
import com.ChiaraPodio.cart_service.dto.CartResponseDTO;
import com.ChiaraPodio.cart_service.dto.SaleDetailsDTO;
import com.ChiaraPodio.cart_service.model.Cart;
import com.ChiaraPodio.cart_service.model.SaleDetails;

import java.util.List;

public interface ICartService {

    public void saveCart(Cart cart);

    public Cart findCart(Long cart_id);

    public List<Cart> getCarts();

    public CartResponseDTO editCart(Long cart_id, CartDTO cartDTO);

    public void deleteCart(Long cart_id);

    public CartResponseDTO createCart (CartDTO cartDTO);

    public List<SaleDetails> createDetails (Cart cart, List<SaleDetailsDTO> detailsListDTO);


}
