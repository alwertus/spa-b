package com.tretsoft.spa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Modem {

    @Id @GeneratedValue
    Long id;

    @Column
    String port;

}
