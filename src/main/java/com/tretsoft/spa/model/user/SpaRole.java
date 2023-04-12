package com.tretsoft.spa.model.user;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @Column
    private Calendar created;

    @Column
    private Boolean isDefault;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpaRole spaRole = (SpaRole) o;

        return name.equals(spaRole.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
