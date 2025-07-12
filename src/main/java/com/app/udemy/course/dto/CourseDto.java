package com.app.udemy.course.dto;

import com.app.udemy.reviews.dto.ReviewsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private Integer id;
    private String title;
    private String imgUrl;
    private float price;
    private String description;
    private String instructorName;
    private int StudentsNumber;
    private int QuantityRating;
    private float AvgRating;
    private List<ReviewsDto> reviews;

}
