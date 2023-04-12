package com.tretsoft.spa.model.doings;


import com.tretsoft.spa.model.user.SpaUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "do_task_list", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "name"})})
public class DoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Calendar startDate;

    @Column(columnDefinition = "boolean default true")
    private Boolean checked;

    @ManyToMany
//    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "do_task_label",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"task_id", "label_id"})}
    )
    private List<DoLabel> labels;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", nullable = false)
    private SpaUser user;

    @Override
    public String toString() {
        return "DoTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + (startDate == null ? "null" : startDate.getTimeInMillis()) +
                ", checked=" + checked +
                '}';
    }
}
