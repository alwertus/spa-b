package com.tretsoft.spa.old.feeding.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "feeding_props")
public class FeedingProperties {
    @Id
    String id;

    @Column(name = "interval_hour", nullable = false, columnDefinition = "integer default 2")
    int intervalHour = 2;

    @Column(name = "interval_min", nullable = false, columnDefinition = "integer default 0")
    int intervalMin = 0;
}
