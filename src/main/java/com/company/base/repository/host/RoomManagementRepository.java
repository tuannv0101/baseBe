package com.company.base.repository.host;

import com.company.base.dto.response.host.RoomMatrixProjection;
import com.company.base.entity.RoomManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface RoomManagementRepository extends JpaRepository<RoomManager, Long> {
    List<RoomManager> findByPropertiesIdOrderByFloorAscRoomNumberAsc(Long propertiesId);

    Page<RoomManager> findByPropertiesIdOrderByFloorAscRoomNumberAsc(Long propertiesId, Pageable pageable);

    Page<RoomManager> findAllByOrderByIdDesc(Pageable pageable);

    @Query(
            value = """
                    select
                        r.id as roomId,
                        r.properties_id as propertyId,
                        r.floor as floor,
                        r.room_number as roomNumber,
                        r.status as status,
                        r.price as price,
                        r.area as area,
                        r.type_room as typeRoom,
                        t.full_name as tenantName,
                        t.id_card_number as tenantIdCardNumber
                    from rooms r
                    join properties p
                      on p.id = r.properties_id
                     and (p.del_yn is null or p.del_yn <> 'Y')
                    left join contracts c
                      on cast(c.room_id as unsigned) = r.id
                     and (c.del_yn is null or c.del_yn <> 'Y')
                     and lower(c.status) = 'active'
                    left join tenants t
                      on cast(c.tenant_id as unsigned) = t.id
                     and (t.del_yn is null or t.del_yn <> 'Y')
                    where (r.del_yn is null or r.del_yn <> 'Y')
                      and (:propertyId is null or r.properties_id = :propertyId)
                    order by r.floor asc, r.room_number asc
                    """,
            countQuery = """
                    select count(*)
                    from rooms r
                    join properties p
                      on p.id = r.properties_id
                     and (p.del_yn is null or p.del_yn <> 'Y')
                    left join contracts c
                      on cast(c.room_id as unsigned) = r.id
                     and (c.del_yn is null or c.del_yn <> 'Y')
                     and lower(c.status) = 'active'
                    left join tenants t
                      on cast(c.tenant_id as unsigned) = t.id
                     and (t.del_yn is null or t.del_yn <> 'Y')
                    where (r.del_yn is null or r.del_yn <> 'Y')
                      and (:propertyId is null or r.properties_id = :propertyId)
                    """,
            nativeQuery = true
    )
    Page<RoomMatrixProjection> getRoomMatrix(@Param("propertyId") Long propertyId, Pageable pageable);
}
