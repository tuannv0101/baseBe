package com.company.base.common.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public final class PaginationUtils {
    private PaginationUtils() {
    }

    public static <T> PageResponse<T> paginateList(List<T> allItems, Pageable pageable) {
        if (allItems == null || allItems.isEmpty()) {
            Page<T> empty = Page.empty(pageable);
            return PageResponse.of(empty);
        }

        int page = Math.max(pageable.getPageNumber(), 0);
        int size = pageable.getPageSize() <= 0 ? allItems.size() : pageable.getPageSize();

        int fromIndex = page * size;
        if (fromIndex >= allItems.size()) {
            Page<T> empty = new PageImpl<>(Collections.emptyList(), pageable, allItems.size());
            return PageResponse.of(empty);
        }

        int toIndex = Math.min(fromIndex + size, allItems.size());
        List<T> slice = allItems.subList(fromIndex, toIndex);
        Page<T> pageResult = new PageImpl<>(slice, pageable, allItems.size());
        return PageResponse.of(pageResult);
    }
}

