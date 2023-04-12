package com.tretsoft.spa.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashWalletCellDto {

    private Long id;

    private Long walletId;

    private String name;

    private Boolean hidden;

    private String icon;

    private String notes;

}
