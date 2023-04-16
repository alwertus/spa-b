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
public class CashOperationDto {

    private Long id;

    private CashWalletCellDto walletCellSource;

    private CashWalletCellDto walletCellDestination;

    private CashProductDto product;

    private Long created;

    private Long completeDate;

    private Float sum;

    private Float rate;

    private Float transferFee;

    private String compositeSum;

    private String notes;

    private Boolean isAutofill;

}
