package com.tretsoft.spa.model.info;

public record PageDto(Long id,
                      String title,
                      Long spaceId,
                      Integer position,
                      String html
                              ){
}