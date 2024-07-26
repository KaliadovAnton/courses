package anton.kaliadau.course.impl;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.course.model.Course;
import anton.kaliadau.course.CourseRepository;
import anton.kaliadau.course.CourseService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl() {
        this.repository = BeanFactory.getCourseRepository();
    }

    @Override
    public Course saveCourse(String courseName) {
        return repository.save(courseName);
    }

    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }

    @Override
    public void updateNameById(Long courseId, String newName) {
        repository.updateNameById(courseId, newName);
    }

    @Override
    public void deleteById(Long courseId) {
        repository.deleteById(courseId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Course findById(Long courseId) {
        return repository.findById(courseId).orElseThrow();
    }
}
