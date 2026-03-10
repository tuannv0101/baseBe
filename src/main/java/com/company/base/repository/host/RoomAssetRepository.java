package com.company.base.repository.host;

import com.company.base.entity.RoomAsset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface RoomAssetRepository extends JpaRepository<RoomAsset, Long> {
    List<RoomAsset> findByRoomId(String roomId);

    Page<RoomAsset> findByRoomIdOrderByIdAsc(String roomId, Pageable pageable);
}
