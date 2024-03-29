package com.tretsoft.spa.model.phone;


import com.tretsoft.spa.model.user.SpaUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_phone_config")
public class UserPhoneConfig extends SpaUser {

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="modem_id")
    private Modem modem;

}
