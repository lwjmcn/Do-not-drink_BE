package com.jorupmotte.donotdrink.theme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theme", schema = "do-not-drink")
@Getter
@NoArgsConstructor
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "concept_id", nullable = false)
    private Concept concept;

    @Size(max = 45)
    @NotNull
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Size(max = 225)
    @NotNull
    @Column(name = "description", nullable = false, length = 225)
    private String description;

    @Size(max = 7)
    @NotNull
    @Column(name = "color", nullable = false, length = 7)
    private String color;

    @NotNull
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

}