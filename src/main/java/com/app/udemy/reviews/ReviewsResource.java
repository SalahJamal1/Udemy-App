package com.app.udemy.reviews;

import com.app.udemy.student.StudentResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsResource {
    private String comment;
    private float rating;
    private StudentResource student;
}
