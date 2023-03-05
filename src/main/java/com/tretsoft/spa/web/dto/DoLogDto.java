package com.tretsoft.spa.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoLogDto {
    private Long id;
    private DoTaskDto task;
    private Long startDate;
    private Long endDate;
}
