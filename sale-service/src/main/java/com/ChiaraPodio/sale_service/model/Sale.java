package com.ChiaraPodio.sale_service.model;

import com.ChiaraPodio.sale_service.dto.CartResponseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sale_id;
    private LocalDate sale_date;
    //private CartResponseDTO cartResponse;
    private Long cart_id;
    private Double total_price;
}
