package com.ChiaraPodio.products_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long product_id;
    private String name;
    private String brand;
    private Double current_price;
    private Integer stock;

}
