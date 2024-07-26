package anton.kaliadau.course;

import anton.kaliadau.course.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Course save(String name);
    Optional<Course> findById(Long id);
    void deleteById(Long id);
    void updateNameById(Long id, String newName);

    List<Course> findAll();

    void deleteAll();
}
