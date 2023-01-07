package com.tretsoft.spa.old.feeding.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetNewIntervalRq {
    int hour;
    int min;
}
