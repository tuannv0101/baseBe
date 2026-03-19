package com.company.base.common.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StringIdGenerator {
    private static final int NUMBER_LENGTH = 8;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public synchronized String nextId(String tableName, String prefix) {
        Object currentMaxId = entityManager.createNativeQuery(
                        "select max(id) from " + tableName + " where id like :pattern"
                )
                .setParameter("pattern", prefix + "%")
                .getSingleResult();

        long nextNumber = 1L;
        if (currentMaxId != null) {
            String currentId = currentMaxId.toString();
            nextNumber = Long.parseLong(currentId.substring(prefix.length())) + 1;
        }

        return prefix + String.format("%0" + NUMBER_LENGTH + "d", nextNumber);
    }
}
