package com.tretsoft.spa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="modem_id")
    private Modem modem;

    @Column
    private Calendar created;

    @Column
    private String sender;

    @Column(length = 1024)
    private String message;

    @Column
    private String comment;

    @Column(length = 3)
    private String direction;

    @Column
    private Boolean read;

    @Column
    private String nature;

    @Override
    public String toString() {
        return "Sms{" +
                "\n\tid=" + id +
                ", \n\tmodem='" + modem.getPort() + '\'' +
                ", \n\tnature='" + nature + '\'' +
                ", \n\tcreated=" + (created == null ? "null" : created.getTime()) + '\'' +
                ", \n\tsender='" + sender + '\'' +
                ", \n\tmessage='" + message + '\'' +
                ", \n\tcomment='" + comment + '\'' +
                ", \n\tdirection='" + direction + '\'' +
                ", \n\tread=" + read +
                '}';
    }
}
