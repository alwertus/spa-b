package com.tretsoft.spa.old.feeding.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "feeding")
public class Feeding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_id")
    private String accessId;

    @Column(name = "start")
    private Calendar start;

    @Column(name = "stop")
    private Calendar stop;

    @Column(name = "breast", length = 1)
    private String breast;
}
