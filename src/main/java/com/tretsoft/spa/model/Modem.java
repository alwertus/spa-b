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

    @Column
    String label;

    @Column
    Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Modem modem = (Modem) o;

        return id.equals(modem.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
