package com.tretsoft.spa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class SpaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Column
    private Calendar created;

    @Column
    private Calendar updated;

    @Column
    private Calendar lastLogin;

    @Column
    private SpaUserStatus status;

    @Column
    private String emailConfirmKey;

    @ManyToMany
    private List<SpaRole> roles;

}
