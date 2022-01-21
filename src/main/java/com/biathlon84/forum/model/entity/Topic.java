package com.biathlon84.forum.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "topics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Section section;

    @Column(length = 50)
    private String title;

    @Column(length = 250)
    private String content;

    @Column
    private int views;

    @Column(updatable = false, nullable = false)
    private java.util.Date creationDate;

    @Column
    private java.util.Date lastUpdateDate;

    @Column
    private boolean closed;

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate =  new Date(System.currentTimeMillis());
    }

}
