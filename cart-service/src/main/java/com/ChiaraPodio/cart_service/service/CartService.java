package com.ChiaraPodio.cart_service.service;

import com.ChiaraPodio.cart_service.dto.CartDTO;
import com.ChiaraPodio.cart_service.dto.CartResponseDTO;
import com.ChiaraPodio.cart_service.dto.ProductDTO;
import com.ChiaraPodio.cart_service.dto.SaleDetailsDTO;
import com.ChiaraPodio.cart_service.gateway.ProductGateway;
import com.ChiaraPodio.cart_service.model.Cart;
import com.ChiaraPodio.cart_service.model.SaleDetails;
import com.ChiaraPodio.cart_service.repository.ICartRepository;
import com.ChiaraPodio.cart_service.repository.IProductsAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService{

    @Autowired
    private ICartRepository cartRepo;

    @Autowired
    private IProductsAPI apiProducts;

    @Autowired
    private ProductGateway productGateway;

    @Override
    public void saveCart(Cart cart) {
        cartRepo.save(cart);
    }

    @Override
    public Cart findCart(Long cart_id) {
        Cart cart = cartRepo.findById(cart_id).orElse(null);
        return cart;
    }

    @Override
    public List<Cart> getCarts() {
        List<Cart> cartList = cartRepo.findAll();
        return cartList;
    }

    @Override
    public CartResponseDTO editCart(Long cart_id, CartDTO cartDTO) {
        Cart cart = this.findCart(cart_id);

            //List<SaleDetails> detailsListError = new ArrayList<>();
            for (SaleDetails saleDetail : cart.getDetailsList()) {
                productGateway.addStock(saleDetail.getProduct_id(), saleDetail.getProduct_quantity());
                //  detailsListError.add(saleDetail);
            }

        cart.getDetailsList().clear();

        List<SaleDetails> saleDetailsList = this.createDetails(cart, cartDTO.getSaleDetailsDTOSList());

        cart.getDetailsList().addAll(saleDetailsList);
        cart.setTotal(totalCalculator(cart.getDetailsList()));
        this.saveCart(cart);

        CartResponseDTO cartResponse = new CartResponseDTO();
        cartResponse.setCart_id(cart.getCart_id());
        cartResponse.setTotal_price(cart.getTotal());

        return cartResponse;
    }

    @Override
    public void deleteCart(Long cart_id) {
        cartRepo.deleteById(cart_id);
    }

    @Override
    public CartResponseDTO createCart (CartDTO cartDTO) {
        Cart cart = new Cart();
        List<SaleDetails> saleDetailsList = this.createDetails(cart, cartDTO.getSaleDetailsDTOSList());

        cart.setDetailsList(saleDetailsList);
        cart.setTotal(totalCalculator(cart.getDetailsList()));

        this.saveCart(cart);

        CartResponseDTO cartResponse = new CartResponseDTO();
        cartResponse.setCart_id(cart.getCart_id());
        cartResponse.setTotal_price(cart.getTotal());

        return cartResponse;
    }

    @Override
    @CircuitBreaker(name = "product-service", fallbackMethod = "fallbackCreateDetails")
    public List<SaleDetails> createDetails (Cart cart, List<SaleDetailsDTO> detailsListDTO) {
        List<SaleDetails> saleDetailsList = new ArrayList<>();

        for (SaleDetailsDTO saleDetailDTO : detailsListDTO) {
            SaleDetails saleDetails = new SaleDetails();
            ProductDTO product = apiProducts.getProductById(saleDetailDTO.getProduct_id());

            saleDetails.setCart(cart);
            saleDetails.setProduct_id(product.getProduct_id());
            saleDetails.setProduct_quantity(saleDetailDTO.getProduct_quantity());
            saleDetails.setPrice(product.getCurrent_price());
            saleDetails.setSubtotal(product.getCurrent_price() * saleDetailDTO.getProduct_quantity());
            apiProducts.removeStock(product.getProduct_id(), saleDetailDTO.getProduct_quantity());
            saleDetailsList.add(saleDetails);
        }
        return saleDetailsList;
    }

    public static Double totalCalculator (List<SaleDetails> saleDetailsList) {
        Double total = 0.0;

        for (SaleDetails saleDetail : saleDetailsList) {
            total+= saleDetail.getSubtotal();
        }
        return total;
    }

    public List<SaleDetails> fallbackCreateDetails(Cart cart,
                                                   List<SaleDetailsDTO> detailsListDTO,
                                                   Throwable ex) {
        throw new RuntimeException("En este momento no se puede crear la venta.");
    }

    public CartResponseDTO fallbackEditCart(Long cart_id, CartDTO cartDTO, Throwable ex) {

        throw new RuntimeException("No se pudo editar la venta.");
    }

    public void rollBackEditCart (List<SaleDetails> detailsListError) {
        for (SaleDetails saleDetail : detailsListError) {
            apiProducts.removeStock(saleDetail.getProduct_id(), saleDetail.getProduct_quantity());
        }
    }

}
