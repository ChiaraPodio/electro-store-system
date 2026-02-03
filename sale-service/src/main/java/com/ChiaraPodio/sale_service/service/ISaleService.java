package com.ChiaraPodio.sale_service.service;

import com.ChiaraPodio.sale_service.dto.CartDTO;
import com.ChiaraPodio.sale_service.model.Sale;

import java.time.LocalDate;
import java.util.List;

public interface ISaleService {

    public void saveSale(Sale sale);

    public Sale findSale(Long sale_id);

    public List<Sale> getSales();

    public void editSale(Long sale_id, LocalDate sale_date, CartDTO cartDTO);

    public void deleteSale(Long sale_id);

    public void createSale(CartDTO cartDTO);
}
