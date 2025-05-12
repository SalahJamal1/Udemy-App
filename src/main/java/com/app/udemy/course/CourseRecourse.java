package com.app.udemy.course;

import com.app.udemy.reviews.ReviewsResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRecourse {

    private Integer id;
    private String title;
    private String imgUrl;
    private float price;
    private String description;
    private String instructorName;
    private int StudentsNumber;
    private int QuantityRating;
    private float AvgRating;
    private List<ReviewsResource> reviews;

}
