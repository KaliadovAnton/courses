package anton.kaliadau.coursestudent.impl;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.coursestudent.CourseStudentService;
import anton.kaliadau.student.StudentRepository;

public class CourseStudentServiceImpl implements CourseStudentService {

    private StudentRepository repository;

    public CourseStudentServiceImpl() {
        this.repository = BeanFactory.getStudentRepository();
    }

    @Override
    public void addCourseToStudent(Long studentId, Long courseId) {
        repository.addCourseToStudent(studentId, courseId);
    }
}
