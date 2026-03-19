package com.company.base.repository.host;

import com.company.base.dto.response.host.ContractListProjection;
import com.company.base.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository for data access operations.
 */

public interface ContractRepository extends JpaRepository<Contract, String> {
    List<Contract> findByStatusIgnoreCaseOrderByStartDateDescIdDesc(String status);

    List<Contract> findByStatusInOrderByStartDateDescIdDesc(Collection<String> statuses);

    List<Contract> findByEndDateBetweenAndStatusIgnoreCaseOrderByEndDateAscIdDesc(LocalDate fromDate, LocalDate toDate, String status);

    List<Contract> findByTenantIdOrderByStartDateDescIdDesc(String tenantId);

    Optional<Contract> findFirstByTenantIdAndStatusIgnoreCaseOrderByStartDateDescIdDesc(String tenantId, String status);

    Optional<Contract> findFirstByRoomIdAndStatusIgnoreCaseOrderByStartDateDescIdDesc(String roomId, String status);

    boolean existsByRoomIdAndStatusIgnoreCaseAndIdNot(String roomId, String status, String id);

    boolean existsByRoomIdAndStatusIgnoreCase(String roomId, String status);

    Page<Contract> findAllByOrderByStartDateDescIdDesc(Pageable pageable);

    Page<Contract> findByStatusIgnoreCaseOrderByStartDateDescIdDesc(String status, Pageable pageable);

    Page<Contract> findByStatusInOrderByStartDateDescIdDesc(Collection<String> statuses, Pageable pageable);

    @Query(
            value = """
                    select
                        c.id as id,
                        c.id as contractCode,
                        c.room_id as roomId,
                        r.room_number as roomNumber,
                        p.name as propertyName,
                        c.tenant_id as tenantId,
                        t.full_name as tenantName,
                        t.id_card_number as tenantIdCardNumber,
                        c.start_date as startDate,
                        c.end_date as endDate,
                        c.actual_rent as rentAmount,
                        c.status as status
                    from contracts c
                    left join rooms r
                      on r.id = c.room_id
                     and (r.del_yn is null or r.del_yn <> 'Y')
                    left join properties p
                      on p.id = r.properties_id
                     and (p.del_yn is null or p.del_yn <> 'Y')
                    left join tenants t
                      on t.id = c.tenant_id
                     and (t.del_yn is null or t.del_yn <> 'Y')
                    where (c.del_yn is null or c.del_yn <> 'Y')
                      and (
                            :textSearch is null or :textSearch = ''
                            or lower(c.id) like concat('%', lower(:textSearch), '%')
                            or lower(t.full_name) like concat('%', lower(:textSearch), '%')
                            or lower(t.id_card_number) like concat('%', lower(:textSearch), '%')
                      )
                    order by c.start_date desc, c.id desc
                    """,
            countQuery = """
                    select count(*)
                    from contracts c
                    left join tenants t
                      on t.id = c.tenant_id
                     and (t.del_yn is null or t.del_yn <> 'Y')
                    where (c.del_yn is null or c.del_yn <> 'Y')
                      and (
                            :textSearch is null or :textSearch = ''
                            or lower(c.id) like concat('%', lower(:textSearch), '%')
                            or lower(t.full_name) like concat('%', lower(:textSearch), '%')
                            or lower(t.id_card_number) like concat('%', lower(:textSearch), '%')
                      )
                    """,
            nativeQuery = true
    )
    Page<ContractListProjection> searchContracts(
            @Param("textSearch") String textSearch,
            Pageable pageable
    );
}

