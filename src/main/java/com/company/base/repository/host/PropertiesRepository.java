package com.company.base.repository.host;

import com.company.base.dto.response.host.PropertyRoomStatsProjection;
import com.company.base.dto.response.host.roomManager.ListRoomResProjection;
import com.company.base.entity.PropertiesManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for data access operations.
 */

public interface PropertiesRepository extends JpaRepository<PropertiesManager, Long> {
    Page<PropertiesManager> findAllByOrderByNameAsc(Pageable pageable);

    List<PropertiesManager> findAllByOrderByNameAsc();

    Page<PropertiesManager> findByNameContainingIgnoreCaseOrderByNameAsc(String name, Pageable pageable);

    @Query(
            value = """
                    select
                        p.id as id,
                        p.name as name,
                        p.address as address,
                        p.total_floors as totalFloors,
                        coalesce(sum(case when lower(r.status) = 'occupied' then 1 else 0 end), 0) as occupiedRooms,
                        coalesce(sum(case when lower(r.status) = 'maintenance' then 1 else 0 end), 0) as maintenanceRooms,
                        coalesce(sum(case when lower(r.status) = 'available' then 1 else 0 end), 0) as availableRooms
                    from properties p
                    left join rooms r
                      on r.properties_id = p.id
                     and (r.del_yn is null or r.del_yn <> 'Y')
                    where (p.del_yn is null or p.del_yn <> 'Y')
                    group by p.id, p.name, p.address, p.total_floors
                    order by p.name asc
                    """,
            countQuery = """
                    select count(*)
                    from properties p
                    where (p.del_yn is null or p.del_yn <> 'Y')
                    """,
            nativeQuery = true
    )
    Page<PropertyRoomStatsProjection> findAllWithRoomStats(Pageable pageable);

    @Query(
            value = """
                    select
                        r.id as id,
                        r.room_number as roomNumber,
                        p.name as propertyName,
                        r.type_room as typeRoom,
                        r.price as price,
                        t.full_name as tenantName,
                        t.id_card_number as tenantIdCardNumber,
                        r.status as statusRoom
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
                      and (:nameProperty is null or :nameProperty = '' or lower(p.name) like concat('%', lower(:nameProperty), '%'))
                      and (:statusRoom is null or :statusRoom = '' or lower(r.status) = lower(:statusRoom))
                      and (:typeRoom is null or :typeRoom = '' or lower(r.type) = lower(:typeRoom))
                      and (:tenantName is null or :tenantName = '' or lower(t.full_name) like concat('%', lower(:tenantName), '%'))
                      and (:priceRoom is null or :priceRoom = '' or r.price = :priceRoom)
                    order by p.name asc, r.room_number asc
                    """,
            countQuery = """
                select count(distinct r.id)
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
                  and (:nameProperty is null or :nameProperty = '' or lower(p.name) like concat('%', lower(:nameProperty), '%'))
                  and (:statusRoom is null or :statusRoom = '' or lower(r.status) = lower(:statusRoom))
                  and (:typeRoom is null or :typeRoom = '' or lower(r.type) = lower(:typeRoom))
                  and (:tenantName is null or :tenantName = '' or lower(t.full_name) like concat('%', lower(:tenantName), '%'))
                  and (:priceRoom is null or :priceRoom = '' or r.price = :priceRoom)
                """,
            nativeQuery = true
    )
    Page<ListRoomResProjection> searchProperties(
            @Param("nameProperty") String nameProperty,
            @Param("statusRoom") String statusRoom,
            @Param("typeRoom") String typeRoom,
            @Param("tenantName") String tenantName,
            @Param("priceRoom") String priceRoom,
            Pageable pageable
    );
}
