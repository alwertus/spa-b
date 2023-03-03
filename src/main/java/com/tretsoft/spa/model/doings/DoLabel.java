package com.tretsoft.spa.model.doings;

import com.tretsoft.spa.model.SpaUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "do_label_list", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
public class DoLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String name;

    @Column
    private String color;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", nullable = false)
    private SpaUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoLabel label = (DoLabel) o;
        // if ID exists - compare with id
        if (id != null && label.id != null) return id.equals(label.id);

        // else compare with name&user
        if (!Objects.equals(name, label.name)) return false;
        return Objects.equals(user, label.user);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DoLabel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
