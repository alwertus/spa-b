package com.tretsoft.spa.model.info;

import com.tretsoft.spa.model.SpaUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Lazy;

import java.util.Calendar;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "info_page")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="space_id")
    private Space space;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="parent_id")
    private Page parent;

    @Column(name = "title")
    private String title;

    @Lob
    @Lazy
    @Column(name = "html", columnDefinition = "TEXT")
    private String html;

    @ColumnDefault("0")
    @Column(name = "position")
    private Integer position;

    @Column(name = "created")
    @CreationTimestamp
    private Calendar created;

    @Column(name = "updated")
    @CreationTimestamp
    private Calendar updated;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "created_by")
    private SpaUser createdBy;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "updated_by")
    private SpaUser updatedBy;

    public PageListItemDto toPageListItemDto() {
        return new PageListItemDto(id, title, position, space.getId(), parent != null ? parent.getId() : null, null);
    }
    public PageDto toPageDto() {
        return new PageDto(id, title, space.getId(), html);
    }
}
