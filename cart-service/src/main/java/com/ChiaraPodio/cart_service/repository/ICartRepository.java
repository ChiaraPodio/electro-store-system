package com.ChiaraPodio.cart_service.repository;

import com.ChiaraPodio.cart_service.model.Cart;
import com.ChiaraPodio.cart_service.model.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository <Cart, Long> {

}
