package anton.kaliadau.util;

import anton.kaliadau.coordinator.CoordinatorRepository;
import anton.kaliadau.coordinator.CoordinatorService;
import anton.kaliadau.coordinator.impl.CoordinatorRepositoryImpl;
import anton.kaliadau.coordinator.impl.CoordinatorServiceImpl;
import anton.kaliadau.course.CourseRepository;
import anton.kaliadau.course.CourseService;
import anton.kaliadau.course.impl.CourseRepositoryImpl;
import anton.kaliadau.course.impl.CourseServiceImpl;
import anton.kaliadau.coursestudent.CourseStudentService;
import anton.kaliadau.coursestudent.impl.CourseStudentServiceImpl;
import anton.kaliadau.student.StudentRepository;
import anton.kaliadau.student.StudentService;
import anton.kaliadau.student.impl.StudentRepositoryImpl;
import anton.kaliadau.student.impl.StudentServiceImpl;

public class BeanFactory {

    private static StudentRepository studentRepository;
    private static StudentService studentService;
    private static CourseService courseService;
    private static CourseRepository courseRepository;
    private static CoordinatorService coordinatorService;
    private static CoordinatorRepository coordinatorRepository;
    private static CourseStudentService courseStudentService;

    public static StudentRepository getStudentRepository() {
        if (studentRepository == null) {
            studentRepository = new StudentRepositoryImpl();
        }
        return studentRepository;
    }

    public static StudentService getStudentService() {
        if (studentService == null) {
            studentService = new StudentServiceImpl();
        }
        return studentService;
    }

    public static CourseService getCourseService() {
        if (courseService == null) {
            courseService = new CourseServiceImpl();
        }
        return courseService;
    }

    public static CourseRepository getCourseRepository() {
        if (courseRepository == null) {
            courseRepository = new CourseRepositoryImpl();
        }
        return courseRepository;
    }

    public static CoordinatorService getCoordinatorService() {
        if (coordinatorService == null) {
            coordinatorService = new CoordinatorServiceImpl();
        }
        return coordinatorService;
    }

    public static CoordinatorRepository getCoordinatorRepository() {
        if (coordinatorRepository == null) {
            coordinatorRepository = new CoordinatorRepositoryImpl();
        }
        return coordinatorRepository;
    }

    public static CourseStudentService getCourseStudentService() {
        if (courseStudentService == null) {
            courseStudentService = new CourseStudentServiceImpl();
        }
        return courseStudentService;
    }
}
