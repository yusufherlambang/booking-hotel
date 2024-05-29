package com.BookingHotel.service;

import com.BookingHotel.dto.rooms.RoomGridDTO;
import com.BookingHotel.dto.rooms.RoomInsertDTO;
import com.BookingHotel.dto.rooms.RoomUpdateDTO;
import com.BookingHotel.entity.Room;
import com.BookingHotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Page<RoomGridDTO> getAllRoom(Pageable pageable, String roomNumber) {

        return roomRepository.findAll(pageable, roomNumber);
    }

    @Override
    public RoomUpdateDTO getRoomToUpdate(Long id) {

        Optional<Room> roomOptional = roomRepository.findById(id);

        Room roomTemp = roomOptional.get();

        RoomUpdateDTO roomUpdateDTO = new RoomUpdateDTO(
                roomTemp.getId(),
                roomTemp.getRoomNumber(),
                roomTemp.getRoomType(),
                roomTemp.getPrice()
        );

        return roomUpdateDTO;
    }

    @Override
    public void insertRoom(RoomInsertDTO roomInsertDTO) {
        Room newRoom = new Room(
                roomInsertDTO.getRoomNumber(),
                roomInsertDTO.getRoomType(),
                roomInsertDTO.getPrice(),
                "vacant"
        );
        System.out.println("new room = "+newRoom);
        roomRepository.save(newRoom);
    }

    @Override
    public boolean checkExistingRoomNumber(Long idValue, String roomValue) {
        idValue = (idValue == null) ? 0l : idValue;
        Long totalData = roomRepository.count(idValue, roomValue);
//        System.out.println(totalData);

        return totalData > 0;
    }

    @Override
    public void updateRoom(RoomUpdateDTO roomUpdateDTO) {

        Optional<Room> optionalRoom = roomRepository.findById(roomUpdateDTO.getId());

        Room roomTemp = null;

        if (optionalRoom.isPresent()){
            roomTemp = optionalRoom.get();
            roomTemp.setRoomNumber(roomUpdateDTO.getRoomNumber());
            roomTemp.setRoomType(roomUpdateDTO.getRoomType());
            roomTemp.setPrice(roomUpdateDTO.getPrice());
        }

        System.out.println("Room Update = "+ roomTemp);

        roomRepository.save(roomTemp);
    }

    @Override
    public Page<RoomGridDTO> getAllVacantRoom(Pageable pageable, String statusRoom, String roomNumber, String roomType) {

        return roomRepository.findVacantRoom(pageable, statusRoom, roomNumber, roomType);
    }

    @Override
    public Room findRoom(String roomNumber) {
        Optional<Room> roomOptional = roomRepository.getRoomByRoomNumber(roomNumber);

        Room roomTemp = null;

        if (roomOptional.isPresent()){
            roomTemp = roomOptional.get();
        }

        return roomTemp;
    }

    @Override
    public Room setStatusRoomToVacant(Room room) {
        room.setRoomStatus("vacant");

        roomRepository.save(room);

        return room;
    }

    @Override
    public Room getRoomById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);

        Room roomTemp = null;

        if (roomOptional.isPresent()){
            roomTemp = roomOptional.get();
        }

        return roomTemp;
    }

    @Override
    public void deleteRoomById(Long id) {

        roomRepository.deleteById(id);
    }
}
