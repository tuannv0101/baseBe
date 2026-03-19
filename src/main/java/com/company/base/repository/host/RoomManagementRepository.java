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

public interface RoomManagementRepository extends JpaRepository<RoomManager, String> {
    List<RoomManager> findByPropertiesIdOrderByFloorAscRoomNumberAsc(String propertiesId);

    Page<RoomManager> findByPropertiesIdOrderByFloorAscRoomNumberAsc(String propertiesId, Pageable pageable);

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
                      on c.id = (
                            select c2.id
                            from contracts c2
                            where c2.room_id = r.id
                              and (c2.del_yn is null or c2.del_yn <> 'Y')
                              and lower(c2.status) = 'active'
                            order by c2.start_date desc, c2.id desc
                            limit 1
                      )
                    left join tenants t
                      on c.tenant_id = t.id
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
                    where (r.del_yn is null or r.del_yn <> 'Y')
                      and (:propertyId is null or r.properties_id = :propertyId)
                    """,
            nativeQuery = true
    )
    Page<RoomMatrixProjection> getRoomMatrix(@Param("propertyId") String propertyId, Pageable pageable);
}


