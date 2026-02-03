package com.ChiaraPodio.sale_service.controller;

import com.ChiaraPodio.sale_service.dto.CartDTO;
import com.ChiaraPodio.sale_service.model.Sale;
import com.ChiaraPodio.sale_service.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleServ;

    @PostMapping("/save")
    public void saveSale (@RequestBody CartDTO cartDTO) {
        saleServ.createSale(cartDTO);
    }

    @GetMapping("/{sale_id}")
    public Sale findSale (@PathVariable Long sale_id) {
        return saleServ.findSale(sale_id);
    }

    @GetMapping("/all")
    public List<Sale> getSales() {
        return saleServ.getSales();
    }

    @PutMapping("/edit/{sale_id}")
    public void editSale(@PathVariable Long sale_id,
                         @RequestParam LocalDate sale_date,
                         @RequestBody CartDTO cartDTO) {
         saleServ.editSale(sale_id, sale_date, cartDTO);
    }

    @DeleteMapping("/delete/{sale_id}")
    public void deleteSale(@PathVariable Long sale_id) {
        saleServ.deleteSale(sale_id);
    }

}
