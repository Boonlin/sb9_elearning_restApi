package co.istad.elearningapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // elearning:read, elearning:write, ...

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;
}
