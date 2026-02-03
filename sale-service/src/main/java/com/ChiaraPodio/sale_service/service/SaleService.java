package com.ChiaraPodio.sale_service.service;

import com.ChiaraPodio.sale_service.dto.CartDTO;
import com.ChiaraPodio.sale_service.dto.CartResponseDTO;
import com.ChiaraPodio.sale_service.model.Sale;
import com.ChiaraPodio.sale_service.repository.ICartAPI;
import com.ChiaraPodio.sale_service.repository.ISaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepo;

    @Autowired
    private ICartAPI apiCart;

    @Override
    public void saveSale(Sale sale) {
        saleRepo.save(sale);
    }

    @Override
    public Sale findSale(Long sale_id) {
        Sale sale = saleRepo.findById(sale_id).orElse(null);
        return sale;
    }

    @Override
    public List<Sale> getSales() {
        List<Sale> saleList = saleRepo.findAll();
        return saleList;
    }

    @Override
    public void editSale(Long sale_id, LocalDate sale_date, CartDTO cartDTO) {
        Sale sale = this.findSale(sale_id);

        if (sale_date != null) {
            sale.setSale_date(sale_date);}
        if (cartDTO != null) {
            Long cart_id = sale.getCart_id();
            CartResponseDTO cartResponse = apiCart.editCart(cart_id, cartDTO);
            sale.setCart_id(cartResponse.getCart_id());
            sale.setTotal_price(cartResponse.getTotal_price());
        }

        this.saveSale(sale);
    }

    @Override
    public void deleteSale(Long sale_id) {
        saleRepo.deleteById(sale_id);
    }

    @Override
    public void createSale(CartDTO cartDTO) {
        Sale sale = new Sale();
        sale.setSale_date(LocalDate.now());
        CartResponseDTO cartResponse = apiCart.saveCart(cartDTO);
        sale.setCart_id(cartResponse.getCart_id());
        sale.setTotal_price(cartResponse.getTotal_price());

        this.saveSale(sale);
    }
}
