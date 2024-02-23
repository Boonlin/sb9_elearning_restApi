package co.istad.elearningapi.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String thumbnail;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Boolean isFree;
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "ins_id", referencedColumnName = "id")
    private Instructor instructor;

}
