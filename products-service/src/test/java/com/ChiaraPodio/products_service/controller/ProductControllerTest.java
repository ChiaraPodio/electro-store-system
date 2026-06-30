package com.ChiaraPodio.products_service.controller;

import com.ChiaraPodio.products_service.model.Product;
import com.ChiaraPodio.products_service.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private IProductService productServ;

    @Test
    public void getProductsTest () throws Exception {
        Product product = new Product(null, "Smart TV 55 Inch 4K", "Samsung", 899.99, 15);
        when(productServ.getProducts()).thenReturn(List.of(product));

        mvc.perform(get("/product/all").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Smart TV 55 Inch 4K"))
                .andExpect(jsonPath("$[0].brand").value("Samsung"))
                .andExpect(jsonPath("$[0].current_price").value(899.99))
                .andExpect(jsonPath("$[0].stock").value(15));

    }
}
