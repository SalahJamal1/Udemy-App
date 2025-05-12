package com.app.udemy.reviews;

import com.app.udemy.course.Course;
import com.app.udemy.student.Students;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    @Min(value = 1, message = "this filed is required")
    @Max(value = 5, message = "this filed is required")
    private float rating;
    @ManyToOne
    @JoinColumn(name = "students_id")
    @JsonIgnore
    private Students students;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
}
