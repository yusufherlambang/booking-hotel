package com.BookingHotel.service;

import com.BookingHotel.entity.Reservation;
import com.BookingHotel.repository.ReservationRepository;
import com.BookingHotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UpdateStatusOtomatisImpl implements UpdateStatusOtomatis{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @EventListener(ContextRefreshedEvent.class) //eksekusi method saat aplikasi berjalan
    @Scheduled(cron = "0 0 0 * * *")            //eksekusi method sesuai jadwal yang ditentukan (s,min,h,d,m,dayOfWeek)
    @Override                                   //format cron ini menyatakan jadwal eksekusinya pada jam 00:00
    public void updateStatus() {

        //findAllCurrentReservation
        LocalDate today = LocalDate.now();

        List<Reservation> reservations = reservationRepository.findAll(); // kalo server pernah mati
//        List<Reservation> reservations = reservationRepository.findAllCurrentReservation(today); // kalo server hidup terus

        reservations.forEach(reservation -> {

            //filter berdasarkan reservationStatus = confirm.
            if (reservation.getReservationStatus().equals("confirmed")){

                // reservationStatus = CONFIRMED
                if (reservation.getRoom().getRoomStatus().equals("reserved")){

                    // roomStatus = RESERVED
                    // jika hari ini >= checkIn & hari ini <= checkOut ubah statusRoom reserved -> occupied
                    if (today.isEqual(reservation.getCheckIn()) || today.isAfter(reservation.getCheckIn())){
                        reservation.getRoom().setRoomStatus("occupied");
                    }
                }else{
                    // roomStatus = OCCUPIED
                    // jika hari ini > checkOut ubah statusRoom occupied -> vacant
                    if (today.isAfter(reservation.getCheckOut())){
                        reservation.getRoom().setRoomStatus("vacant");
                    }
                }

                roomRepository.save(reservation.getRoom());
                reservationRepository.save(reservation);
            }else{
                // reservationStatus = PENDING
                // jika hari ini > checkIn
                // maka ubah reservationStatus pending -> expired & roomStatus reserved -> vacant
                if (today.isAfter(reservation.getCheckIn())){
                    reservation.setReservationStatus("expired");
                    reservation.getRoom().setRoomStatus("vacant");
                }

                roomRepository.save(reservation.getRoom());
                reservationRepository.save(reservation);
            }
        });
        System.out.println("Automatic Update Success ");
    }
}
