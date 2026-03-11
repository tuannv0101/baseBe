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
    List<RoomAsset> findByRoomId(Long roomId);

    Page<RoomAsset> findByRoomIdOrderByIdAsc(Long roomId, Pageable pageable);

    List<RoomAsset> findByRoomIdAndDelYn(Long roomId, String delYn);

    Page<RoomAsset> findByRoomIdAndDelYnOrderByIdAsc(Long roomId, String delYn, Pageable pageable);
}
