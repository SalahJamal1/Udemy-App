package com.app.udemy.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
