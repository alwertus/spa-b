package com.tretsoft.spa.model.doings;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "do_log")
public class DoLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private DoTask task;

    @Column(nullable = false)
    private Calendar startDate;

    @Column(nullable = false)
    private Calendar endDate;

    @Override
    public String toString() {
        return "DoLog{" +
                "id=" + id +
                ", task=" + task.getId() +
                ", startDate=" + startDate.getTimeInMillis() +
                ", endDate=" + endDate.getTimeInMillis() +
                '}';
    }
}
