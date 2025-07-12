package com.app.udemy.course;


import com.app.udemy.instructor.Instructor;
import com.app.udemy.reviews.Reviews;

import com.app.udemy.reviews.dto.ReviewsDto;
import com.app.udemy.student.Students;
import com.app.udemy.student.dto.StudentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    @JsonIgnore
    private Instructor instructor;
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Students> students;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    @JsonIgnore
    private List<Reviews> reviews = new ArrayList<>();

    public float AvgRating(Course course) {
        return (float) course.getReviews()
                .stream()
                .mapToDouble(Reviews::getRating)
                .average().orElse(0.0);
    }

    public List<ReviewsDto> ReviewsDto(Course course) {
        return course.getReviews().stream()
                .map(r -> ReviewsDto.builder()
                        .comment(r.getComment())
                        .rating(r.getRating()).student(StudentDto
                                .builder().id(r.getStudents().getId())
                                .name(r.getStudents().getName()).build()).build()).toList();
    }

}
