package com.company.base.controller.host;

import com.company.base.common.ApiResponse;
import com.company.base.common.pagination.PageResponse;
import com.company.base.dto.request.host.BillingServiceRequest;
import com.company.base.dto.request.host.PropertyServiceBulkUpdateRequest;
import com.company.base.dto.response.host.BillingServiceResponse;
import com.company.base.exception.AppException;
import com.company.base.service.BillingManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/host/property-management/properties/{propertyId}/services")
@RequiredArgsConstructor
public class PropertyServiceController {

    private final BillingManagementService billingManagementService;

    @PostMapping
    public ApiResponse<List<BillingServiceResponse>> createServices(
            @PathVariable String propertyId,
            @RequestBody List<BillingServiceRequest> requests
    ) {
        if (requests == null || requests.isEmpty()) {
            return ApiResponse.success(List.of());
        }

        List<BillingServiceResponse> created = requests.stream().map(req -> {
            BillingServiceRequest r = req != null ? req : new BillingServiceRequest();
            r.setPropertyId(propertyId);
            return billingManagementService.createService(r);
        }).toList();
        return ApiResponse.success(created);
    }

    @PutMapping
    public ApiResponse<List<BillingServiceResponse>> updateServices(
            @PathVariable String propertyId,
            @RequestBody List<PropertyServiceBulkUpdateRequest> requests
    ) {
        if (requests == null || requests.isEmpty()) {
            return ApiResponse.success(List.of());
        }

        List<BillingServiceResponse> updated = requests.stream().map(req -> {
            if (req == null || req.getServiceId() == null || req.getServiceId().isBlank()) {
                throw new AppException(HttpStatus.BAD_REQUEST.value(), "serviceId is required");
            }
            String serviceId = req.getServiceId();

            BillingServiceResponse existing = billingManagementService.getServiceById(serviceId);
            assertBelongsToProperty(propertyId, existing);

            BillingServiceRequest r = BillingServiceRequest.builder()
                    .propertyId(propertyId)
                    .name(req.getName())
                    .unitPrice(req.getUnitPrice())
                    .unitType(req.getUnitType())
                    .build();
            return billingManagementService.updateService(serviceId, r);
        }).toList();

        return ApiResponse.success(updated);
    }

    @GetMapping("/{serviceId}")
    public ApiResponse<BillingServiceResponse> getServiceById(
            @PathVariable String propertyId,
            @PathVariable String serviceId
    ) {
        BillingServiceResponse res = billingManagementService.getServiceById(serviceId);
        assertBelongsToProperty(propertyId, res);
        return ApiResponse.success(res);
    }

    @GetMapping
    public ApiResponse<PageResponse<BillingServiceResponse>> getServicesByProperty(
            @PathVariable String propertyId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ApiResponse.success(billingManagementService.getAllServices(propertyId, pageable));
    }

    @PutMapping("/{serviceId}/delete")
    public ApiResponse<Void> deleteService(
            @PathVariable String propertyId,
            @PathVariable String serviceId
    ) {
        BillingServiceResponse existing = billingManagementService.getServiceById(serviceId);
        assertBelongsToProperty(propertyId, existing);
        billingManagementService.deleteService(serviceId);
        return ApiResponse.success(null);
    }

    private void assertBelongsToProperty(String propertyId, BillingServiceResponse service) {
        if (service == null || service.getPropertyId() == null || !service.getPropertyId().equals(propertyId)) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Service not found");
        }
    }
}
