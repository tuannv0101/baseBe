package com.company.base.service;

import com.company.base.dto.request.host.PropertyRequest;
import com.company.base.dto.response.host.PropertyResponse;
import com.company.base.dto.response.host.RoomMatrixResponse;

import java.util.List;

/**
 * Service contract defining operations for this module.
 */

public interface PropertyManagementService {
    PropertyResponse createProperty(PropertyRequest request);

    PropertyResponse updateProperty(Long id, PropertyRequest request);

    PropertyResponse getPropertyById(Long id);

    List<PropertyResponse> getAllProperties();

    void deleteProperty(Long id);

    List<RoomMatrixResponse> getRoomMatrix(Long propertyId);
}
