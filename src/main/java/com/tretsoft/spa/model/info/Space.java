package com.tretsoft.spa.model.info;

import com.tretsoft.spa.model.SpaUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "info_space")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "created")
    private Calendar created;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "created_by")
    private SpaUser createdBy;

    @Column(name = "is_private")
    private Boolean isPrivate;

    public SpaceDto toDto() {
        return new SpaceDto(id, title, isPrivate == null || isPrivate);
    }

}
