package com.BookingHotel.controller;


import com.BookingHotel.dto.rooms.RoomGridDTO;
import com.BookingHotel.dto.rooms.RoomInsertDTO;
import com.BookingHotel.dto.rooms.RoomUpdateDTO;
import com.BookingHotel.entity.Reservation;
import com.BookingHotel.entity.Room;
import com.BookingHotel.service.ReservationService;
import com.BookingHotel.service.RoomService;
import com.BookingHotel.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomControllerMvc {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/index")
    public String roomIndex(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "") String roomNumber,
                            Model model ){

        Pageable pageable = PageRequest.of(page - 1, 2, Sort.by("id"));

        Page<RoomGridDTO> roomGrid = roomService.getAllRoom(pageable,roomNumber);

        model.addAttribute("roomGrid", roomGrid.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomGrid.getTotalPages());
        model.addAttribute("breadCrumbs", "Super Admin & Admin");
        model.addAttribute("roomNumber", roomNumber);

        return "room/room-index";
    }

    @GetMapping("/upsertForm")
    public String RoomUpsertForm(@RequestParam(required = false) Long id,
                                 Model model){

        if(id != null){
            RoomUpdateDTO dto = roomService.getRoomToUpdate(id);

            model.addAttribute("room", dto);
            model.addAttribute("type","update");
            model.addAttribute("typeDropdown", Dropdown.getRoomType());
            model.addAttribute("breadCrumbs","Room Index / Update Room");
        } else {
            RoomInsertDTO dto = new RoomInsertDTO();

            model.addAttribute("room", dto);
            model.addAttribute("type","insert");
            model.addAttribute("typeDropdown", Dropdown.getRoomType());
            model.addAttribute("breadCrumbs","Room Index / Insert Room");
        }

        return "room/room-upsertForm";
    }

    @PostMapping("/insert")
    public String insertRoom(@Valid @ModelAttribute("room") RoomInsertDTO roomInsertDTO,
                             BindingResult bindingResult,
                             Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("type","insert");
            model.addAttribute("typeDropdown", Dropdown.getRoomType());
            model.addAttribute("breadCrumbs","Super Admin & Admin / Insert Room");

            return "room/room-upsertForm";
        }else{
            roomService.insertRoom(roomInsertDTO);

            return "redirect:/room/index";
        }
    }

    @PostMapping("/update")
    public String updateRoom(@Valid @ModelAttribute("room") RoomUpdateDTO roomUpdateDTO,
                             BindingResult bindingResult,
                             Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("type","update");
            model.addAttribute("typeDropdown", Dropdown.getRoomType());
            model.addAttribute("breadCrumbs","Super Admin & Admin / Update Room");

            return "room/room-upsertForm";
        } else{
            roomService.updateRoom(roomUpdateDTO);

            return "redirect:/room/index";
        }
    }


    //get room with status vacant
    @GetMapping("/vacant")
    public String roomVacant(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "") String roomNumber,
                             @RequestParam(defaultValue = "") String roomType,
                             Model model) {

        Pageable pageable = PageRequest.of(page -1, 2, Sort.by("id"));

        String statusRoom = "vacant";

        Page<RoomGridDTO> listRoom = roomService.getAllVacantRoom(pageable, statusRoom, roomNumber, roomType);

        model.addAttribute("roomGrid", listRoom.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listRoom.getTotalPages());
        model.addAttribute("breadCrumbs", "Customer");
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("roomType", roomType);
        model.addAttribute("typeDropdown", Dropdown.getRoomType());

        return "room/room-vacant";
    }

    @GetMapping("/delete")
    public String deleteRoomVacant(@RequestParam(required = true) Long id,
                                   Model model){

        //get room by id
        Room room = roomService.getRoomById(id);

        if (room != null){

            //check apakah roomStatus = vacant
            String vacant = "vacant";
            String status = room.getRoomStatus();

            if (status.equals(vacant)){

                Long roomId = room.getId();

                //check apakah ada history transaksi atau tidak
                // dengan cara : check reservation by roomId
                List<Reservation> reservation = reservationService.getReservationByRoomId(roomId);
                System.out.println("size room reservation = " + reservation.size());

                if (reservation.size() == 0){

                    roomService.deleteRoomById(id);

                    return "redirect:/room";
                }else{

                    return "room/cannot-delete-room";
                }
            }else{

                return "room/cannot-delete-room";
            }
        }else{

            model.addAttribute("breadCrumbs", "Fail to Delete Room");
            return "notFound/delete-id-notFound";
        }
    }
}
