package com.example.assignment.controller;

import com.example.assignment.model.Course;
import com.example.assignment.model.CourseInstance;
import com.example.assignment.service.CourseInstanceService;
import com.example.assignment.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instances")
public class CourseInstanceController {

    @Autowired
    private CourseInstanceService courseInstanceService;

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<?> createInstance(@RequestBody CourseInstance instance) {
        // Fetch the course from the database to ensure it exists
        Optional<Course> courseOptional = courseService.getCourseById(instance.getCourse().getId());
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with ID " + instance.getCourse().getId() + " not found.");
        }

        instance.setCourse(courseOptional.get());
        CourseInstance createdInstance = courseInstanceService.createCourseInstance(instance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstance);
    }

    @GetMapping("/{year}/{semester}")
    public ResponseEntity<List<CourseInstance>> getCoursesByYearAndSemester(@PathVariable int year, @PathVariable int semester) {
        List<CourseInstance> instances = courseInstanceService.getCourseInstancesByYearAndSemester(year, semester);
        return ResponseEntity.ok(instances);
    }

    @GetMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> getCourseInstance(@PathVariable int year, @PathVariable int semester, @PathVariable Long courseId) {
        CourseInstance instance = courseInstanceService.getCourseInstance(year, semester, courseId);
        if (instance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course instance with ID " + courseId + " not found.");
        }
        return ResponseEntity.ok(instance);
    }

    @DeleteMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> deleteCourseInstance(@PathVariable int year, @PathVariable int semester, @PathVariable Long courseId) {
        boolean deleted = courseInstanceService.deleteCourseInstance(year, semester, courseId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course instance with ID " + courseId + " not found.");
        }
        return ResponseEntity.noContent().build();
    }
}
