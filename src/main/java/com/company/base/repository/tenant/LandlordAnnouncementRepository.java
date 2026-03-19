package com.company.base.repository.tenant;

import com.company.base.entity.LandlordAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for data access operations.
 */
public interface LandlordAnnouncementRepository extends JpaRepository<LandlordAnnouncement, String> {
    List<LandlordAnnouncement> findTop10ByRoomIdAndActiveTrueOrderByCreatedAtDescIdDesc(String roomId);
    List<LandlordAnnouncement> findTop10ByActiveTrueOrderByCreatedAtDescIdDesc();
}

