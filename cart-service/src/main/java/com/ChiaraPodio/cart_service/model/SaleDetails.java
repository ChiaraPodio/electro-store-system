package com.ChiaraPodio.cart_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
// no deberia ser una entidad si no tiene repository ni nada la esta persistiendo
public class SaleDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long detail_id;
    private Long product_id;
    private Integer product_quantity;
    private Double price;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

}
