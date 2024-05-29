package com.BookingHotel.controller;

import com.BookingHotel.dto.customers.CustomerUpdateDTO;
import com.BookingHotel.dto.reservations.*;
import com.BookingHotel.entity.Customer;
import com.BookingHotel.entity.Reservation;
import com.BookingHotel.entity.Room;
import com.BookingHotel.service.CustomerService;
import com.BookingHotel.service.ReservationService;
import com.BookingHotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/reservation")
public class ReservationControllerMvc {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    // reserve form
    @GetMapping("/reserveForm")
    public String reservationForm(@RequestParam String roomNumber,
                                  HttpServletRequest request,
                                  Model model){

        String username = request.getUserPrincipal().getName();

//        String username2 = SecurityContextHolder.getContext().getAuthentication().getName();

        Boolean isReservationExist = reservationService.isCustomerHasReservation(username);

        System.out.println("ada ngga = "+isReservationExist);

        if (isReservationExist){

            model.addAttribute("breadCrumbs", "Cannot Reserve");

            return "reservation/cannot-reserve";
        }else{

            // get customer by username
            Customer customer = customerService.findCustomer(username);

            // get room by roomNumber
            Room room = roomService.findRoom(roomNumber);

            ReservationInsertDTO dto = new ReservationInsertDTO();

            dto.setCustomerId(customer.getId());
            dto.setRoomId(room.getId());
            dto.setRoomNumber(room.getRoomNumber());
            dto.setRoomType(room.getRoomType());
            dto.setPrice(room.getPrice());

            model.addAttribute("reservation", dto);
            model.addAttribute("priceRupiah", dto.getPriceRupiah());
            model.addAttribute("breadCrumbs", "Customer Reserve");
            model.addAttribute("type", "reserve");

            return "reservation/reservation-insert-form";
        }
    }

    @PostMapping("/reserve")
    public String reserveVacantRoom(@Valid @ModelAttribute("reservation") ReservationInsertDTO dto,
                                    BindingResult bindingResult,
                                    Model model){

        System.out.println("isi reservationDTO = "+ dto);

        if (bindingResult.hasErrors()){

            model.addAttribute("priceRupiah", dto.getPriceRupiah());
            model.addAttribute("breadCrumbs", "Customer Reserve");
            model.addAttribute("type", "reserve");

            return "reservation/reservation-insert-form";
        }else{

            reservationService.insertReservation(dto);

            return "redirect:/reservation/my-reservation";
        }
    }

    @GetMapping("/my-reservation")
    public String getMyReservation(HttpServletRequest request,
                                   Model model){

        String username = request.getUserPrincipal().getName();

        // find customerId by username
        Customer customer = customerService.findCustomer(username);

        // find customer reservation
        MyReservationDTO myReservationDTO = reservationService.getCustomerReservation(customer.getId());
        System.out.println("ini My reservation = "+myReservationDTO);

        if (myReservationDTO == null){

            model.addAttribute("breadCrumbs", "My Reservation Not Exist");

            return "reservation/my-reservation-not-exist";
        }else{

            model.addAttribute("myReservation", myReservationDTO);
            model.addAttribute("type", "confirm");
            model.addAttribute("breadCrumbs", "My Reservation");

            return "reservation/my-reservation";
        }
    }


    @PostMapping("/confirm")
    public String confirmReservation(@ModelAttribute("myReservation") MyReservationDTO myReservationDTO){

        Long id = myReservationDTO.getId();

        reservationService.updateReservationStatusToConfirm(id);

        return "redirect:/reservation/my-reservation";
    }

    @GetMapping("/delete")
    public String deleteReservationWithStatusPending(@RequestParam(required = true) Long id,
                                           HttpServletRequest request,
                                           Model model){

        String usernameLogin = request.getUserPrincipal().getName();

        Reservation reservation = reservationService.getReservationById(id);

        if (reservation != null){

            String usernameReservation = reservation.getCustomer().getAccount().getUsername();

            //kondisi jika ada orang yang delete beda dari yang login
            if (usernameReservation.equals(usernameLogin)){

                //delete reservation
                reservationService.deletePendingReservationById(id);

                //cari room agar bisa set statusRoom dari reserved ke vacant
                Room room = reservation.getRoom();

                roomService.setStatusRoomToVacant(room);

                return "redirect:/reservation/my-reservation";
            }else {

                return "account/access-denied";
            }
        }else{

            model.addAttribute("breadCrumbs", "Fail to Delete Reservation");
            return "notFound/delete-id-notFound";
        }
    }

    @GetMapping("/transaction/customer")
    public String transactionCustomerById(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(required = true) Long id,
                                          Model model){

        //check id customer ada apa tidak
        Customer customer = customerService.getCustomerById(id);

        if (customer != null){

            Pageable pageable = PageRequest.of(page -1, 2, Sort.by("id"));

            //getAll reservation by customer id
            Page<ReservationCustomerGridDTO> reservationCustomerGridDTOS = reservationService.getAllReservationByCustId(pageable, id);

            if (reservationCustomerGridDTOS.getContent().size() != 0){
                model.addAttribute("reservationCustomerGrid", reservationCustomerGridDTOS.getContent());
                model.addAttribute("currentPage", page);
                model.addAttribute("id", id);
                model.addAttribute("totalPages", reservationCustomerGridDTOS.getTotalPages());
                model.addAttribute("breadCrumbs", "Super Admin & Admin");
                model.addAttribute("customerName", customer.getName());
                model.addAttribute("role", "adminOrSuperAdmin");

                return "reservation/customer-transaction";
            }else{
                String role = "AdminOrSuperAdmin";

                model.addAttribute("role", role);
                model.addAttribute("breadCrumbs", "Super Admin & Admin");

                return "reservation/my-transaction-not-exist";
            }
        }else{

            model.addAttribute("breadCrumbs", "Customer Id Not Found");

            return "notFound/customer-id-notFound";
        }
    }

    @GetMapping("/current-reservation")
    public String getCurrentReservation(@RequestParam(required = true) String roomNumber,
                                        Model model){
        //get roomId by roomNumber
        Room room = roomService.findRoom(roomNumber);

        if (room != null){

            Long roomId = room.getId();

            CurrentReservationDTO currentReservation = reservationService.findByRoomId(roomId);

            if (currentReservation != null){

                model.addAttribute("currentReservation", currentReservation);
                model.addAttribute("breadCrumbs", "Current Reservation");

                return "reservation/current-reservation-room";
            }else{

                model.addAttribute("breadCrumbs", "Current Reservation Not Found");
                model.addAttribute("roomNumber", roomNumber);

                return "notFound/current-reservation-notFound";
            }
        }else{

            model.addAttribute("breadCrumbs", "Current Reservation Not Found");
            model.addAttribute("roomNumber", roomNumber);

            return "notFound/current-reservation-notFound";
        }
    }

    @GetMapping("/all-current-reservation")
    public String allCurrentReservation(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue="") String customerName,
                                        @RequestParam(defaultValue="") String roomNumber,
                                        Model model){

        Pageable pageable = PageRequest.of(page, 2, Sort.by("id"));

        Page<AllCurrentReservationDTO> allCurrentReservationDTOS = reservationService.getAllCurrentReservation(pageable, customerName, roomNumber);

        model.addAttribute("allCurrentReservationGrid",allCurrentReservationDTOS.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", allCurrentReservationDTOS.getTotalPages());
        model.addAttribute("breadCrumbs", "Super Admin & Admin");
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("customerName",customerName);

        return "reservation/all-current-reservation";
    }

    //delete reservationById yang checkOutDate nya >= today dan reservationStatus Pending / expired
    @GetMapping("/all-current-reservation/delete")
    public String deleteCurrentReservationAndReservationNotConfirmed(@RequestParam(required = true) Long id,
                                                                                            Model model){
        // findReservationById
        Reservation reservation = reservationService.getReservationById(id);

        if (reservation != null){

            // filter reservation yang checkOutDatenya lebih dari today
            LocalDate today = LocalDate.now();

            if (reservation.getCheckOut().isAfter(today)){

                // filter reservation yang reservationStatus nya pending/expired
                if (!reservation.getReservationStatus().equals("confirmed")){

                    reservationService.deleteCurrentReservationById(id);

                    //set room jadi vacant
                    Room room = reservation.getRoom();

                    roomService.setStatusRoomToVacant(room);

                    return "redirect:/reservation/all-current-reservation";

                }else{

                    model.addAttribute("message", "Fail to delete because Current Reservation Status is CONFIRMED");
                    model.addAttribute("breadCrumbs", "Current Reservation Status Is Confirmed");
                    return "reservation/cannot-delete-currentReservation";
                }

            }else{

                model.addAttribute("message", "Fail to delete because it's already a transaction history");
                model.addAttribute("breadCrumbs", "Check Out Date Before Today");
                return "reservation/cannot-delete-currentReservation";
            }
        }else {

            model.addAttribute("breadCrumbs", "Id Not Found");
            return "notFound/delete-id-notFound";
        }
    }

    @GetMapping("/my-transaction")
    public String getAllReservationByUsernameAndStatusNotExpired(@RequestParam(defaultValue = "1") Integer page,
                                                                 HttpServletRequest request,
                                                                 Model model){
        //get usename currentLogin
        String usernameLogin = request.getUserPrincipal().getName();

        //get customerId ByUsername to find reservation by customerId
        CustomerUpdateDTO customer = customerService.getCustomerByUsername(usernameLogin);
        Long customerId = customer.getId();

        System.out.println("ini data cutomer yang login "+customer);

        //get reservation by customerId where reservationStatus Confirmed
        Pageable pageable = PageRequest.of(page -1, 2, Sort.by("id"));

        //getAll reservation by customer id
        Page<ReservationCustomerGridDTO> reservationCustomerGridDTOS = reservationService.getAllReservationByCustId(pageable, customerId);
        System.out.println("ada transaksi ? "+reservationCustomerGridDTOS.getContent().size());

        if (reservationCustomerGridDTOS.getContent().size() != 0){
            model.addAttribute("reservationCustomerGrid", reservationCustomerGridDTOS.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", reservationCustomerGridDTOS.getTotalPages());
            model.addAttribute("breadCrumbs", "My Transaction (customer)");
            model.addAttribute("customerName", customer.getName());

            return "reservation/my-transaction";
        }else{
            String role = "customer";

            model.addAttribute("breadCrumbs", "My Transaction (customer)");
            model.addAttribute("role", role);

            return "reservation/my-transaction-not-exist";
        }
    }

}
