package com.tretsoft.spa.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class SpaUser implements UserDetails {

    @Id
    @GeneratedValue
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<SpaRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(e -> new SimpleGrantedAuthority(e.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
