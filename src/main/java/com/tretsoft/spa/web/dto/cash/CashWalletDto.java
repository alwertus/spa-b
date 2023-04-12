package com.tretsoft.spa.web.dto.cash;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashWalletDto {

    private Long id;

    private String name;

    private CurrencyDto currency;

    private Boolean hidden;

    List<CashWalletCellDto> cells;

}
