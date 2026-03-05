package com.company.base.repository.host;

import com.company.base.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByPropertiesIdOrderByFloorAscRoomNumberAsc(Long propertiesId);
}
