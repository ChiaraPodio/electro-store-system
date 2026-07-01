package com.ChiaraPodio.products_service.controller;

import com.ChiaraPodio.products_service.dto.ProductRequestDto;
import com.ChiaraPodio.products_service.model.Product;
import com.ChiaraPodio.products_service.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private IProductService productServ;

    @Test
    public void createProductTest() throws Exception {

        String json = """
            {
                "name":"Smart TV 55 Inch 4K",
                "brand":"Samsung",
                "current_price":899.99,
                "stock":15
            }
            """;

        mvc.perform(post("/product/save")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(productServ).createProduct(any(ProductRequestDto.class));
    }

    @Test
    public void findProductTest() throws Exception {

        Product product = new Product(
                1L,
                "Smart TV 55 Inch 4K",
                "Samsung",
                899.99,
                15
        );

        when(productServ.findProduct(1L)).thenReturn(product);

        mvc.perform(get("/product/1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Smart TV 55 Inch 4K"))
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.current_price").value(899.99))
                .andExpect(jsonPath("$.stock").value(15));

        verify(productServ).findProduct(1L);
    }

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

    @Test
    public void editProductTest() throws Exception {

        Product product = new Product(
                1L,
                "Smart TV 55 Inch 4K",
                "LG",
                1200.0,
                20
        );

        when(productServ.editProduct(eq(1L), any(ProductRequestDto.class)))
                .thenReturn(product);

        String json = """
            {
                "brand":"LG",
                "current_price":1200.0,
                "stock":20
            }
            """;

        mvc.perform(patch("/product/1")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("LG"))
                .andExpect(jsonPath("$.current_price").value(1200.0))
                .andExpect(jsonPath("$.stock").value(20));

        verify(productServ).editProduct(eq(1L), any(ProductRequestDto.class));
    }

    @Test
    public void deleteProductTest() throws Exception {

        mvc.perform(delete("/product/delete/1"))
                .andExpect(status().isOk());

        verify(productServ).deleteProduct(1L);
    }

    @Test
    public void removeStockTest() throws Exception {

        mvc.perform(put("/product/remove/stock/1/5"))
                .andExpect(status().isOk());

        verify(productServ).removeStock(1L,5);
    }

    @Test
    public void addStockTest() throws Exception {

        mvc.perform(put("/product/add/stock/1/5"))
                .andExpect(status().isOk());

        verify(productServ).addStock(1L,5);
    }
}
