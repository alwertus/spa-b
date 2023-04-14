package com.tretsoft.spa.web.dto.cash;

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
public class CashProductDto {

    private Long id;

    private String name;

    private Integer grade;

    private CashWalletCellDto defaultCashWalletCellSource;

    private CashWalletCellDto defaultCashWalletCellDestination;

}
