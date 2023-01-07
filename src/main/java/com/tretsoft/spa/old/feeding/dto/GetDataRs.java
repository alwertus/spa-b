package com.tretsoft.spa.old.feeding.dto;

import com.tretsoft.spa.old.common.dto.ResponseOk;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetDataRs extends ResponseOk {

    @Getter
    private final List<FeedingDto> feedingList;

    @Getter
    private final Long lastTimerId;

    @Getter
    private final String interval;
}
