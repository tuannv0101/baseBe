package com.company.base.repository.host;

import com.company.base.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface RoomManagementRepository extends JpaRepository<Room, Long> {
    List<Room> findByPropertiesIdOrderByFloorAscRoomNumberAsc(Long propertiesId);

    Page<Room> findByPropertiesIdOrderByFloorAscRoomNumberAsc(Long propertiesId, Pageable pageable);

    Page<Room> findAllByOrderByIdDesc(Pageable pageable);
}
