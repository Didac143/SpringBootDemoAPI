package com.example.springbootdemoapi.integration_test.reservation;

import com.example.springbootdemoapi.model.Client;
import com.example.springbootdemoapi.model.Product;
import com.example.springbootdemoapi.model.Reservation;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.ClientVO;
import com.example.springbootdemoapi.model.vo.ProductVO;
import com.example.springbootdemoapi.model.vo.ReservationVO;
import com.example.springbootdemoapi.service.ClientService;
import com.example.springbootdemoapi.service.ProductService;
import com.example.springbootdemoapi.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;

    @Test
    void saveReservationWithValidProduct() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId());
        Product savedProduct = productService.addProduct(productToSave);

        String userNickname = "User1";
        Long productId = savedProduct.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity = 3;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);


        Reservation savedReservation = reservationService.addReservation(reservationToSave);


        assertNotNull(savedReservation);
        assertNotNull(savedReservation.getId());
        assertEquals(userNickname, savedReservation.getUserNickname());
        assertEquals(productId, savedReservation.getProductId());
        assertEquals(reservationDate, savedReservation.getDate());
        assertEquals(quantity, savedReservation.getQuantity());
    }

    @Test
    void saveReservationWithNotValidProduct() {
        ReservationVO invalidReservationToSave = new ReservationVO("User1",
                1L, LocalDate.now(), 1);

        assertThrows(RequestException.class, () -> reservationService.addReservation(invalidReservationToSave));
    }

    @Test
    void getReservations() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave1 = new ProductVO("Product1", BigDecimal.valueOf(10), savedClient.getId()),
                productToSave2 = new ProductVO("Product2", BigDecimal.valueOf(20), savedClient.getId());
        Product savedProduct1 = productService.addProduct(productToSave1),
                savedProduct2 = productService.addProduct(productToSave2);

        String userNickname1 = "User1",
                userNickname2 = "User2",
                userNickname3 = "User3";
        Long productId1 = savedProduct1.getId(),
                productId2 = savedProduct2.getId(),
                productId3 = savedProduct1.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity1 = 3, quantity2 = 5, quantity3 = 2;

        ReservationVO reservationToSave1 = new ReservationVO(userNickname1, productId1, reservationDate, quantity1),
                reservationToSave2 = new ReservationVO(userNickname2, productId2, reservationDate, quantity2),
                reservationToSave3 = new ReservationVO(userNickname3, productId3, reservationDate, quantity3);

        List<Reservation> expectedReservations = new ArrayList<>();
        expectedReservations.add(reservationService.addReservation(reservationToSave1));
        expectedReservations.add(reservationService.addReservation(reservationToSave2));
        expectedReservations.add(reservationService.addReservation(reservationToSave3));

        List<Reservation> savedReservations = reservationService.getAllReservations();

        assertNotNull(savedReservations);
        assertEquals(expectedReservations.size(), savedReservations.size());
        assertTrue(savedReservations.containsAll(expectedReservations));
    }

    @Test
    void getReservationByValidId() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId());
        Product savedProduct = productService.addProduct(productToSave);

        String userNickname = "User1";
        Long productId = savedProduct.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity = 3;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);

        Reservation expectedReservation = reservationService.addReservation(reservationToSave);
        Reservation savedReservation = reservationService.getReservationById(expectedReservation.getId());

        assertNotNull(savedReservation);
        assertEquals(expectedReservation.getId(), savedReservation.getId());
        assertEquals(expectedReservation, savedReservation);
    }

    @Test
    void getReservationByNotValidId() {
        assertThrows(IllegalArgumentException.class, () -> reservationService.getReservationById(null));
        assertThrows(RequestException.class, () -> reservationService.getReservationById(1L));
        assertThrows(RequestException.class, () -> reservationService.getReservationById(-1L));
    }

    @Test
    void updateExistingReservationUserNickname() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId());
        Product savedProduct = productService.addProduct(productToSave);

        String userNickname = "User1",
                updatedUserNickname = "User2";
        Long productId = savedProduct.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity = 3;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);
        Reservation savedReservation = reservationService.addReservation(reservationToSave);

        ReservationVO reservationVOToUpdate = new ReservationVO();
        reservationVOToUpdate.setUserNickname(updatedUserNickname);
        Reservation updatedReservation = reservationService.updateReservation(
                savedReservation.getId(),
                reservationVOToUpdate);

        assertNotNull(updatedReservation);
        assertEquals(updatedUserNickname, updatedReservation.getUserNickname());
        assertEquals(productId, updatedReservation.getProductId());
        assertEquals(reservationDate, updatedReservation.getDate());
        assertEquals(quantity, updatedReservation.getQuantity());
    }

    @Test
    void updatedExistingReservationDate() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId());
        Product savedProduct = productService.addProduct(productToSave);

        String userNickname = "User1";
        Long productId = savedProduct.getId();
        LocalDate reservationDate = LocalDate.now(),
                updatedDate = LocalDate.of(2022, 2, 15);
        int quantity = 3;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);
        Reservation savedReservation = reservationService.addReservation(reservationToSave);

        ReservationVO reservationVOToUpdate = new ReservationVO();
        reservationVOToUpdate.setDate(updatedDate);
        Reservation updatedReservation = reservationService.updateReservation(
                savedReservation.getId(),
                reservationVOToUpdate);

        assertNotNull(updatedReservation);
        assertEquals(userNickname, updatedReservation.getUserNickname());
        assertEquals(productId, updatedReservation.getProductId());
        assertEquals(updatedDate, updatedReservation.getDate());
        assertEquals(quantity, updatedReservation.getQuantity());
    }

    @Test
    void updateExistingReservationQuantity() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId());
        Product savedProduct = productService.addProduct(productToSave);

        String userNickname = "User1";
        Long productId = savedProduct.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity = 3,
                updatedQuantity = 5;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);
        Reservation savedReservation = reservationService.addReservation(reservationToSave);

        ReservationVO reservationVOToUpdate = new ReservationVO();
        reservationVOToUpdate.setQuantity(updatedQuantity);
        Reservation updatedReservation = reservationService.updateReservation(
                savedReservation.getId(),
                reservationVOToUpdate);

        assertNotNull(updatedReservation);
        assertEquals(userNickname, updatedReservation.getUserNickname());
        assertEquals(productId, updatedReservation.getProductId());
        assertEquals(reservationDate, updatedReservation.getDate());
        assertEquals(updatedQuantity, updatedReservation.getQuantity());
    }

    @Test
    void updateExistingReservationWithValidProduct() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave1 = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId()),
                productToSave2 = new ProductVO("Product2",
                        BigDecimal.valueOf(20),
                        savedClient.getId());
        Product savedProduct1 = productService.addProduct(productToSave1),
                savedProduct2 = productService.addProduct(productToSave2);

        String userNickname = "User1";
        Long productId = savedProduct1.getId(),
                updatedProductId = savedProduct2.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity = 3;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);
        Reservation savedReservation = reservationService.addReservation(reservationToSave);

        ReservationVO reservationVOToUpdate = new ReservationVO();
        reservationVOToUpdate.setProductId(updatedProductId);
        Reservation updatedReservation = reservationService.updateReservation(
                savedReservation.getId(),
                reservationVOToUpdate);

        assertNotNull(updatedReservation);
        assertEquals(userNickname, updatedReservation.getUserNickname());
        assertEquals(updatedProductId, updatedReservation.getProductId());
        assertEquals(reservationDate, updatedReservation.getDate());
        assertEquals(quantity, updatedReservation.getQuantity());
    }

    @Test
    void updateExistingReservationWithNotValidProduct() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId());
        Product savedProduct = productService.addProduct(productToSave);

        String userNickname = "User1";
        Long productId = savedProduct.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity = 3;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);
        Reservation savedReservation = reservationService.addReservation(reservationToSave);

        ReservationVO reservationVOToUpdate = new ReservationVO();
        reservationVOToUpdate.setProductId(2L);

        assertThrows(RequestException.class, () -> reservationService.updateReservation(
                savedReservation.getId(), reservationVOToUpdate));
    }

    @Test
    void updateNotExistingReservation() {
        assertThrows(RequestException.class, () -> reservationService.updateReservation(
                1L, new ReservationVO()));
    }

    @Test
    void deleteExistingReservation() {
        ClientVO clientToSave = new ClientVO("Client1");
        Client savedClient = clientService.addClient(clientToSave);

        ProductVO productToSave = new ProductVO("Product1",
                BigDecimal.valueOf(10),
                savedClient.getId());
        Product savedProduct = productService.addProduct(productToSave);

        String userNickname = "User1";
        Long productId = savedProduct.getId();
        LocalDate reservationDate = LocalDate.now();
        int quantity = 3;

        ReservationVO reservationToSave = new ReservationVO(userNickname,
                productId, reservationDate, quantity);
        Reservation savedReservation = reservationService.addReservation(reservationToSave);

        Reservation deletedReservation = reservationService.deleteReservation(savedReservation.getId());

        assertNotNull(deletedReservation);
        assertEquals(savedReservation, deletedReservation);
        assertThrows(RequestException.class, () -> reservationService.getReservationById(savedReservation.getId()));
    }

    @Test
    void deleteNotExistingReservation() {
        assertThrows(RequestException.class, () -> reservationService.deleteReservation(1L));
    }

}
