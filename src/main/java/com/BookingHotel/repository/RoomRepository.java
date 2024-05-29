package com.BookingHotel.repository;

import com.BookingHotel.dto.rooms.RoomGridDTO;
import com.BookingHotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            SELECT new com.BookingHotel.dto.rooms.RoomGridDTO(r.id, r.roomNumber, r.roomType, r.price, r.roomStatus)
            FROM Room r
            WHERE r.roomNumber LIKE %:roomNumber%
            """)
    Page<RoomGridDTO> findAll(Pageable pageable,
                              @Param("roomNumber") String roomNumber);

    // checkExistingRoomNumber
    @Query("""
			SELECT COUNT(*)
			FROM Room r
			WHERE r.roomNumber = :roomNumber AND r.id != :id
			""")
    Long count(@Param("id") Long id, @Param("roomNumber") String roomNumber);

    //find all vacant room
    @Query("""
            SELECT new com.BookingHotel.dto.rooms.RoomGridDTO(r.id, r.roomNumber, r.roomType, r.price, r.roomStatus)
            FROM Room r
            WHERE r.roomNumber LIKE %:roomNumber%
                AND r.roomType LIKE %:roomType%
                AND r.roomStatus = :roomStatus
            """)
    Page<RoomGridDTO> findVacantRoom(Pageable pageable,
                                     @Param("roomStatus") String statusRoom,
                                     @Param("roomNumber") String roomNumber,
                                     @Param("roomType") String roomType);

    @Query("""
            SELECT r
            FROM Room r
            WHERE r.roomNumber = :roomNumber
            """)
    Optional<Room> getRoomByRoomNumber(@Param("roomNumber") String roomNumber);
}
