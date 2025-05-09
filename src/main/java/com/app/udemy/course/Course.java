package com.app.udemy.course;


import com.app.udemy.instructor.Instructor;
import com.app.udemy.student.Students;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString(exclude = "students")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String title;
    private String imgUrl;
    private float price;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "instructor_id")
    @JsonBackReference
    private Instructor instructor;
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Students> students;
}
