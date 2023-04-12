package com.tretsoft.spa.old.feeding.model;

import com.tretsoft.spa.model.user.SpaUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "feeding_user_options")
public class FeedingUserOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", unique = true)
    private SpaUser user;

    @Column(name = "access_id")
    private String accessId;
}
