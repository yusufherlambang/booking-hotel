package com.BookingHotel.repository;

import com.BookingHotel.dto.reservations.AllCurrentReservationDTO;
import com.BookingHotel.dto.reservations.CurrentReservationDTO;
import com.BookingHotel.dto.reservations.MyReservationDTO;
import com.BookingHotel.dto.reservations.ReservationCustomerGridDTO;
import com.BookingHotel.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            SELECT rsv
            FROM Reservation rsv
            WHERE rsv.customerId = :customerId
                AND rsv.checkOut >= :today
                AND rsv.reservationStatus != :expired
            """)
    Reservation getReservationByCustomerId(@Param("customerId") Long customerId,
                                           @Param("today") LocalDate today,
                                           @Param("expired") String expired);


    @Query("""
            SELECT new com.BookingHotel.dto.reservations.MyReservationDTO(rsv.id, rm.roomNumber, rm.roomType, rsv.checkIn,
                    rsv.checkOut, rm.price, rsv.bill, rsv.reservationStatus)
            FROM Reservation rsv
                JOIN rsv.room rm
            WHERE rsv.customerId = :customerId
                AND (rsv.reservationStatus = :pending OR rsv.reservationStatus =:confirmed)
                AND rsv.checkOut >= :today
            """)
    MyReservationDTO getReservationByCustIdAndStatusPendingOrConfirm(@Param("customerId")Long customerId,
                                                                     @Param("pending") String pending,
                                                                     @Param("confirmed") String confirmed,
                                                                     @Param("today") LocalDate today);


    @Query("""
            SELECT new com.BookingHotel.dto.reservations.ReservationCustomerGridDTO(rsv.id, rsv.checkIn,
                    rsv.checkOut, rm.roomNumber, rsv.bill)
            FROM Reservation rsv
                JOIN rsv.room rm
            WHERE rsv.customerId = :customerId
                AND rsv.reservationStatus =:confirmed 
                AND rsv.checkOut < :today
            """)
    Page<ReservationCustomerGridDTO> getReservationByCustIdAndStatusConfirm(Pageable pageable,
                                                                            @Param("customerId") Long id,
                                                                            @Param("confirmed") String reservationStatus,
                                                                            @Param("today") LocalDate today);

    @Query("""
            SELECT rsv
            FROM Reservation rsv
            WHERE rsv.roomId = :roomId
            """)
    List<Reservation> findAllReservationByRoomId(@Param("roomId") Long roomId);


    @Query("""
            SELECT new com.BookingHotel.dto.reservations.CurrentReservationDTO(
                rm.roomNumber, rm.roomType, cus.name, rsv.checkIn, rsv.checkOut, rsv.bill, rsv.reservationStatus)
            FROM Reservation rsv
                JOIN rsv.room rm
                JOIN rsv.customer cus
            WHERE rsv.roomId = :roomId   
                AND rsv.checkOut >= :today
                AND rsv.reservationStatus != :expired
            """)
    CurrentReservationDTO findCurrentReservationByRoomId(@Param("roomId") Long roomId,
                                                         @Param("today") LocalDate today,
                                                         @Param("expired") String expired);

    @Query("""
            SELECT rsv
            FROM Reservation rsv
            WHERE rsv.customerId = :customerId
                AND rsv.reservationStatus =:confirmed    
            """)
    List<Reservation> findReservationByCustIdWithStatusConfirm(@Param("customerId") Long id,
                                                               @Param("confirmed") String reservationStatus);

    @Query("""
            SELECT new com.BookingHotel.dto.reservations.AllCurrentReservationDTO(
                rsv.id, cus.name, rm.roomNumber, rsv.checkIn, rsv.checkOut, rsv.bill, rsv.reservationStatus)
            FROM Reservation rsv
                JOIN rsv.customer cus
                JOIN rsv.room rm
            WHERE rsv.checkOut >= :today
                AND cus.name LIKE %:customerName%
                AND rm.roomNumber LIKE %:roomNumber%
            """)
    Page<AllCurrentReservationDTO> findAllCurrentReservationDTO(Pageable pageable,
                                                                @Param("customerName") String customerName,
                                                                @Param("roomNumber") String roomNumber,
                                                                @Param("today") LocalDate today);


    @Query("""
            SELECT rsv
            FROM Reservation rsv
                JOIN rsv.room rm
            WHERE rsv.checkOut >= :today
            """)
    List<Reservation> findAllCurrentReservation(@Param("today")LocalDate today);
}
