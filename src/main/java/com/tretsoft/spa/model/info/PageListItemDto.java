package com.tretsoft.spa.model.info;

import java.util.List;

public record PageListItemDto(Long id,
                              String title,
                              Integer position,
                              Long spaceId,
                              Long parentId,
                              List<PageListItemDto> children
                              ){
}