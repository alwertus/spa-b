package com.tretsoft.spa.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.Calendar;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class SpaRole {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @Column
    private Calendar created;

    @Column
    private Boolean isDefault;
}
