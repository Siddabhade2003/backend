package com.example.assignment.service;

import com.example.assignment.model.CourseInstance;
import com.example.assignment.repository.CourseInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseInstanceService {

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    public CourseInstance createCourseInstance(CourseInstance courseInstance) {
        return courseInstanceRepository.save(courseInstance);
    }

    public List<CourseInstance> getCourseInstancesByYearAndSemester(int year, int semester) {
        return courseInstanceRepository.findByYearAndSemester(year, semester);
    }

    public CourseInstance getCourseInstance(int year, int semester, Long courseId) {
        // Find the CourseInstance by year, semester, and courseId
        Optional<CourseInstance> instance = courseInstanceRepository.findByYearAndSemesterAndCourseId(year, semester, courseId);
        return instance.orElse(null); // Return null if not found
    }

    public boolean deleteCourseInstance(int year, int semester, Long courseId) {
        Optional<CourseInstance> instance = courseInstanceRepository.findByYearAndSemesterAndCourseId(year, semester, courseId);
        if (instance.isPresent()) {
            courseInstanceRepository.delete(instance.get());
            return true; 
        }
        return false; 
    }
}
