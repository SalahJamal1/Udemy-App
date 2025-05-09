package com.app.udemy.course;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>>
    getCourses
            (@RequestParam(value = "title", required = false)
             String title
                    , @RequestParam(value = "page", required = false, defaultValue = "0")
             int page,
             @RequestParam(value = "size", required = false, defaultValue = "6")
             int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Course> courses = title != null ?
                    service.findByTitleContaining(title.toLowerCase(), pageable)
                    : service.findAll(pageable);
            List<CourseRecourse> courseRecourse = courses.getContent().stream()
                    .map(a -> CourseRecourse.builder()
                            .description(a.getDescription())
                            .price(a.getPrice()).imgUrl(a.getImgUrl())
                            .id(a.getId())
                            .instructorName(a.getInstructor().getName())
                            .title(a.getTitle())
                            .StudentsNumber(a.getStudents().size())
                            .build()).toList();

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("status", "success");
            map.put("result", courses.getContent().size());
            map.put("data", courseRecourse);
            map.put("currentPage", courses.getNumber());
            map.put("totalPages", courses.getTotalPages());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(map);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id).orElseThrow(() ->
                        new RuntimeException("We didn't found the doc by Id")));
    }

    @PatchMapping
    public ResponseEntity<Course> updateCourse(@RequestBody Course entity) {
        Course exstingCourse = service.findById(entity.getId()).orElseThrow();
        exstingCourse.setDescription(entity.getDescription());
        exstingCourse.setPrice(entity.getPrice());
        exstingCourse.setTitle(entity.getTitle());
        exstingCourse.setImgUrl(entity.getImgUrl());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(exstingCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        Course entity = service.findById(id).orElseThrow(() -> new RuntimeException("We didn't found the doc by Id"));
        service.delete(entity);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("The doc deleted successfully");
    }
}
