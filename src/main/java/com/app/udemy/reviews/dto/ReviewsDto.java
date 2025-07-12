package com.app.udemy.reviews.dto;

import com.app.udemy.student.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsDto {
    private String comment;
    private float rating;
    private StudentDto student;
}
