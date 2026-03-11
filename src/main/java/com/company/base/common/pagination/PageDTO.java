package com.company.base.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private List<T> items;
    private PageMeta meta;

    public static <T> PageDTO<T> of(Page<T> page) {
        return PageDTO.<T>builder()
                .items(page.getContent())
                .meta(PageMeta.builder()
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .hasNext(page.hasNext())
                        .hasPrevious(page.hasPrevious())
                        .build())
                .build();
    }

    public static <T> PageDTO<T> from(PageResponse<T> pageResponse) {
        if (pageResponse == null) {
            return null;
        }
        return PageDTO.<T>builder()
                .items(pageResponse.getItems())
                .meta(pageResponse.getMeta())
                .build();
    }
}

