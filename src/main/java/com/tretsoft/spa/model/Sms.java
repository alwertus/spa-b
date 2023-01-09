package com.tretsoft.spa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Sms {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="modem_id")
    private Modem modem;

    @Column
    private Calendar created;

    @Column
    private Calendar eventDate;

    @Column
    private String sender;

    @Column
    private String message;

    @Column
    private String comment;

    @Column(length = 3)
    private String direction;
}
