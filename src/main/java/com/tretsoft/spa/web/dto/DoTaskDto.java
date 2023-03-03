package com.tretsoft.spa.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoTaskDto {
    private Long id;
    private String name;
    private Long startDate;
    private Boolean checked;
    private List<DoLabelDto> labels;
}
