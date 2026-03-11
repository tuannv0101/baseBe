package com.company.base.repository.host;

import com.company.base.entity.RoomManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface RoomManagementRepository extends JpaRepository<RoomManager, Long> {
    List<RoomManager> findByPropertiesIdOrderByFloorAscRoomNumberAsc(Long propertiesId);

    Page<RoomManager> findByPropertiesIdOrderByFloorAscRoomNumberAsc(Long propertiesId, Pageable pageable);

    Page<RoomManager> findAllByOrderByIdDesc(Pageable pageable);
}
