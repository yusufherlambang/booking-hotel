package com.BookingHotel.service;

import com.BookingHotel.dto.reservations.*;
import com.BookingHotel.entity.Customer;
import com.BookingHotel.entity.Reservation;
import com.BookingHotel.entity.Room;
import com.BookingHotel.repository.CustomerRepository;
import com.BookingHotel.repository.ReservationRepository;
import com.BookingHotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void insertReservation(ReservationInsertDTO dto) {

        Reservation reservation = new Reservation(
                dto.getCheckIn(),
                dto.getCheckOut(),
                "pending", // null -> pending
                dto.getCustomerId(),
                dto.getRoomId(),
                dto.getTotalPrice()
        );

        System.out.println("ini reservation = " + reservation);

        // set room status dari vacant jadi reserved
        Optional<Room> roomOptional = roomRepository.findById(dto.getRoomId());

        Room tempRoom = null;
        if (roomOptional.isPresent()){
            tempRoom = roomOptional.get();

            tempRoom.setRoomStatus("reserved"); // vacant -> reserved
        }

        System.out.println("ini room = " + tempRoom);

        reservationRepository.save(reservation);
        roomRepository.save(tempRoom);

    }

    @Override
    public Boolean isCustomerHasReservation(String username) {

        // find customerByUsername
        Optional<Customer> customer = customerRepository.findCustByUsername(username);

        Customer customerTemp = customer.get();

        System.out.println("customer = " + customerTemp);

        // find reservationWithCustomerId
        LocalDate today = LocalDate.now();
        String expired = "expired";

        Reservation customerIdReservation = reservationRepository.getReservationByCustomerId(customerTemp.getId(), today, expired);

        return customerIdReservation != null ? true : false;
    }

    @Override
    public MyReservationDTO getCustomerReservation(Long customerId) {

        // find reservation by customerId and reservationStatus pending or confirm
        String pending = "pending";
        String confirm = "confirmed";
        LocalDate today = LocalDate.now();

        MyReservationDTO reservation = reservationRepository.getReservationByCustIdAndStatusPendingOrConfirm(customerId, pending, confirm, today);

        return reservation;
    }

    @Override
    public void updateReservationStatusToConfirm(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        Reservation tempReservation = null;

        if (reservationOptional.isPresent()){
            tempReservation = reservationOptional.get();

            tempReservation.setReservationStatus("confirmed");

            //kondisi jika customer klik button confirm dimana tanggal checkIn = today
            if (tempReservation.getCheckIn().isEqual(LocalDate.now())){

                tempReservation.getRoom().setRoomStatus("occupied");
            }
        }

        System.out.println("ini yang sudah di confirm = "+tempReservation);
        reservationRepository.save(tempReservation);
    }

    @Override
    public Reservation getReservationById(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        Reservation reservationTemp = null;

        if (reservationOptional.isPresent()){
            reservationTemp = reservationOptional.get();
        }

        return reservationTemp;
    }

    @Override
    public void deletePendingReservationById(Long id) {

        reservationRepository.deleteById(id);
    }

    @Override
    public Page<ReservationCustomerGridDTO> getAllReservationByCustId(Pageable pageable, Long id) {

        String reservationStatus = "confirmed";
        LocalDate today = LocalDate.now();

        Page<ReservationCustomerGridDTO> response = reservationRepository.getReservationByCustIdAndStatusConfirm(pageable, id, reservationStatus, today);

        return response;
    }

    @Override
    public List<Reservation> getReservationByRoomId(Long roomId) {

        List<Reservation> listReservation = reservationRepository.findAllReservationByRoomId(roomId);

        return listReservation;
    }

    @Override
    public CurrentReservationDTO findByRoomId(Long roomId) {

        LocalDate today = LocalDate.now();
        String expired = "expired";

        CurrentReservationDTO response = reservationRepository.findCurrentReservationByRoomId(roomId, today, expired);

        System.out.println("ini response repo findCurrentReservationByRoomId = "+response);

        return response;
    }

    @Override
    public List<Reservation> getReservationByCustId(Long customerId) {

        String reservationStatus = "confirmed";

        List<Reservation> reservations = reservationRepository.findReservationByCustIdWithStatusConfirm(customerId, reservationStatus);

        System.out.println("total transaksi customer  repo ="+reservations.size());

        return reservations;
    }

    @Override
    public Page<AllCurrentReservationDTO> getAllCurrentReservation(Pageable pageable, String customerName, String roomNumber) {

        LocalDate today = LocalDate.now();

        Page<AllCurrentReservationDTO> grid = reservationRepository.findAllCurrentReservationDTO(pageable, customerName, roomNumber, today);

//        System.out.println("grid dari repo allCurrentReservation = "+grid.getContent());
        return grid;
    }

    @Override
    public void deleteCurrentReservationById(Long id) {

        reservationRepository.deleteById(id);
    }


}
