package com.company.base.dto.response.host;

/**
 * Interface-based projection for room matrix native query.
 * Spring Data maps column aliases (snake_case ok) to these getters.
 */
public interface RoomMatrixProjection {
    Long getRoomId();

    Long getPropertyId();

    Integer getFloor();

    String getRoomNumber();

    String getStatus();

    String getPrice();

    Float getArea();

    String getTypeRoom();

    String getTenantName();

    String getTenantIdCardNumber();
}

