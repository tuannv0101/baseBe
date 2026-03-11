package com.company.base.dto.response.host.roomManager;

/**
 * Interface-based projection for native queries.
 * Spring Data maps column aliases (snake_case ok) to these getters.
 */
public interface ListRoomResProjection {
    Long getId();

    String getRoomNumber();

    String getPropertyName();

    String getTypeRoom();

    String getPrice();

    String getTenantName();

    String getStatusRoom();
}

