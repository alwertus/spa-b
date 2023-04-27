package com.tretsoft.spa.web.dto.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IconDto {

    private String name;

    private String svg;

}
