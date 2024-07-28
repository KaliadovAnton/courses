package anton.kaliadau.course;

import anton.kaliadau.course.model.Course;

import java.util.List;

public interface CourseService {
    Course saveCourse(String courseName);

    List<Course> findAll();

    void updateNameById(Long courseId, String newName);

    void deleteById(Long courseId);

    void deleteAll();

    Course findById(Long courseId);
}
