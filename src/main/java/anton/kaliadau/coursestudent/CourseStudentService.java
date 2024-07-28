package anton.kaliadau.coursestudent;

import anton.kaliadau.student.model.Student;

public interface CourseStudentService {

    void addCourseToStudent(Long studentId, Long courseId);
}
