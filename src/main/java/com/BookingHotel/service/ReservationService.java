package com.BookingHotel.service;

import com.BookingHotel.dto.reservations.*;
import com.BookingHotel.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    void insertReservation(ReservationInsertDTO dto);

    Boolean isCustomerHasReservation(String username);

    MyReservationDTO getCustomerReservation(Long customerId);

    void updateReservationStatusToConfirm(Long id);

    Reservation getReservationById(Long id);

    void deletePendingReservationById(Long id);

    Page<ReservationCustomerGridDTO> getAllReservationByCustId(Pageable pageable, Long id);

    List<Reservation> getReservationByRoomId(Long roomId);

    CurrentReservationDTO findByRoomId(Long roomId);

    List<Reservation> getReservationByCustId(Long customerId);

    Page<AllCurrentReservationDTO> getAllCurrentReservation(Pageable pageable, String customerName, String roomNumber);

    void deleteCurrentReservationById(Long id);
}
