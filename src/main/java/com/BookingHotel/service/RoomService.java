package com.BookingHotel.service;

import com.BookingHotel.dto.rooms.RoomGridDTO;
import com.BookingHotel.dto.rooms.RoomInsertDTO;
import com.BookingHotel.dto.rooms.RoomUpdateDTO;
import com.BookingHotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    Page<RoomGridDTO> getAllRoom(Pageable pageable, String roomNumber);

    RoomUpdateDTO getRoomToUpdate(Long id);

    void insertRoom(RoomInsertDTO roomInsertDTO);

    boolean checkExistingRoomNumber(Long idValue, String roomValue);

    void updateRoom(RoomUpdateDTO roomUpdateDTO);

    Page<RoomGridDTO> getAllVacantRoom(Pageable pageable, String statusRoom, String roomNumber, String roomType);

    Room findRoom(String roomNumber);

    Room setStatusRoomToVacant(Room room);

    Room getRoomById(Long id);

    void deleteRoomById(Long id);
}
