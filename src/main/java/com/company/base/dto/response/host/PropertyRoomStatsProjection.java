package com.company.base.dto.response.host;

/**
 * Interface-based projection for native queries.
 * Spring Data maps column aliases (snake_case ok) to these getters.
 */
public interface PropertyRoomStatsProjection {
    String getId();

    String getName();

    String getAddress();

    Integer getTotalFloors();

    String getUserId();

    Long getOccupiedRooms();

    Long getMaintenanceRooms();

    Long getAvailableRooms();
}


