package com.example.springbootdemoapi.unit_test.reservation;

import com.example.springbootdemoapi.model.Product;
import com.example.springbootdemoapi.model.Reservation;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.ReservationVO;
import com.example.springbootdemoapi.repository.ReservationRepository;
import com.example.springbootdemoapi.service.ProductService;
import com.example.springbootdemoapi.service.ReservationService;
import com.example.springbootdemoapi.service.impl.ProductServiceImpl;
import com.example.springbootdemoapi.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {
    private final ReservationRepository reservationRepository;
    private final ProductService productService;
    private final ReservationService reservationService;

    public ReservationServiceTest() {
        this.reservationRepository = mock(ReservationRepository.class);
        this.productService = mock(ProductServiceImpl.class);
        this.reservationService = new ReservationServiceImpl(reservationRepository, productService);
    }

    @Test
    void addReservationWithValidProduct() {
        //GIVEN
        String userNickname = "userNickname1";
        Long productId = 25L;
        LocalDate date = LocalDate.now();

        Product product = new Product();
        product.setId(productId);

        ReservationVO reservationVOtoSave = new ReservationVO(userNickname, productId, date, 1);

        Reservation reservationSaved = new Reservation(reservationVOtoSave.getUserNickname(),
                reservationVOtoSave.getProductId(),
                reservationVOtoSave.getDate());

        when(productService.getProductById(reservationVOtoSave.getProductId())).thenReturn(product);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationSaved);
        //WHEN -> executing business logic

        //THEN -> asserts
        assertEquals(reservationSaved, reservationService.addReservation(reservationVOtoSave));
    }

    @Test
    void addReservationWithNotValidProduct() {
        //GIVEN
        String userNickname = "userNickname1";
        Long productId = 25L;
        LocalDate date = LocalDate.now();

        ReservationVO reservationVOtoSave = new ReservationVO(userNickname, productId, date, 1);

        when(productService.getProductById(reservationVOtoSave.getProductId())).thenThrow(RequestException.class);
        //WHEN -> executing business logic

        //THEN -> asserts
        assertThrows(RequestException.class, () -> reservationService.addReservation(reservationVOtoSave));
    }

    @Test
    void updateReservationWithNotValidId() {
        Long reservationId = 100L;
        ReservationVO reservationVOtoUpdateReservation = new ReservationVO();

        when(reservationRepository.findById(reservationId)).thenThrow(RequestException.class);

        assertThrows(RequestException.class, () -> reservationService.updateReservation(
                reservationId, reservationVOtoUpdateReservation));
    }

    @Test
    void updateReservationWithValidId() {
        //GIVEN
        Long reservationId = 1L;
        String updatedUserNickname = "updatedUserNickname";
        LocalDate updatedDate = LocalDate.of(2020, 2, 15);
        int updatedQuantity = 5;
        Long updatedProductId = 10L;

        String chunk;
        String[] parts;
        Reservation reservationAux;
        ReservationVO reservationVOAux;

        List<Reservation> reservationsToAssert = new ArrayList<>();
        List<ReservationVO> reservationVOCombinations = new ArrayList<>();
        List<String> values = new ArrayList<>();

        values.add(updatedUserNickname);
        values.add(updatedDate.toString());
        values.add(String.valueOf(updatedQuantity));
        values.add(String.valueOf(updatedProductId));

        for (int i=0; i<16; i++) {
            reservationVOCombinations.add(new ReservationVO());
            reservationsToAssert.add(new Reservation());
        }

        for (int i = 0; i < 16; i++) {
            chunk = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
            parts = chunk.split("");
            for (int j = 0; j < parts.length; j++) {
                reservationVOAux = reservationVOCombinations.get(i);
                reservationAux = reservationsToAssert.get(i);
                String value = values.get(j);
                if ("1".equals(parts[j])) {
                    switch (j) {
                        case 0:
                            reservationAux.setUserNickname(value);
                            reservationVOAux.setUserNickname(value);
                            break;
                        case 1:
                            reservationAux.setDate(LocalDate.parse(value));
                            reservationVOAux.setDate(LocalDate.parse(value));
                            break;
                        case 2:
                            reservationAux.setQuantity(Integer.parseInt(value));
                            reservationVOAux.setQuantity(Integer.parseInt(value));
                            break;
                        case 3:
                            reservationAux.setProductId(Long.valueOf(value));
                            reservationVOAux.setProductId(Long.valueOf(value));
                            break;
                    }
                }
            }
        }

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(new Reservation()));

        for (int i = 0; i < reservationsToAssert.size(); i++) {

            when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationVOCombinations.get(i).toReservation());

            assertEquals(reservationsToAssert.get(i).getUserNickname(),
                    reservationService.updateReservation(reservationId, reservationVOCombinations.get(i)).getUserNickname());
            assertEquals(reservationsToAssert.get(i).getDate(),
                    reservationService.updateReservation(reservationId, reservationVOCombinations.get(i)).getDate());
            assertEquals(reservationsToAssert.get(i).getProductId(),
                    reservationService.updateReservation(reservationId, reservationVOCombinations.get(i)).getProductId());
            assertEquals(reservationsToAssert.get(i).getQuantity(),
                    reservationService.updateReservation(reservationId, reservationVOCombinations.get(i)).getQuantity());
        }
    }

    @Test
    void updateReservationWithNotValidProduct() {
        Long reservationId = 20L, productId = 100L;

        ReservationVO reservationVOtoUpdate = new ReservationVO();
        reservationVOtoUpdate.setProductId(productId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(new Reservation()));
        when(productService.getProductById(productId)).thenThrow(RequestException.class);

        assertThrows(RequestException.class, () -> reservationService.updateReservation(reservationId, reservationVOtoUpdate));

    }

    @Test
    void getAllReservations() {
        List<Reservation> reservations = new ArrayList<>(Arrays.asList(new Reservation(), new Reservation()));

        when(reservationRepository.findAll()).thenReturn(reservations);

        assertEquals(reservations, reservationService.getAllReservations());
    }

    @Test
    void getReservationByExistingId() {
        Long reservationId = 20L;

        Reservation requestedReservation = new Reservation();
        requestedReservation.setId(reservationId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(requestedReservation));

        assertEquals(reservationId, reservationService.getReservationById(reservationId).getId());
    }

    @Test
    void getReservationByNotExistingId() {
        Long reservationId = 50L;

        when(reservationRepository.findById(reservationId)).thenThrow(RequestException.class);

        assertThrows(RequestException.class, () -> reservationService.getReservationById(reservationId));
    }

    @Test
    void deleteReservationWithExistingId() {
        Long reservationId = 25L;
        Reservation reservationToDelete = new Reservation();

         when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservationToDelete));

         assertEquals(reservationToDelete, reservationService.deleteReservation(reservationId));
    }

    @Test
    void deleteReservationWithNotExistingId() {
        Long reservationId = 130L;

        when(reservationRepository.findById(reservationId)).thenThrow(RequestException.class);

        assertThrows(RequestException.class, () -> reservationService.deleteReservation(reservationId));
    }
}
