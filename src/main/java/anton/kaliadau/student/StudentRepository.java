package anton.kaliadau.student;

import anton.kaliadau.student.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(String studentName);

    void deleteById(Long studentId);

    void updateNameById(Long studentId, String newName);

    List<Student> findAll();

    void deleteAll();

    Optional<Student> updateCoordinator(Long studentId, Long coordinatorId);

    void addCourseToStudent(Long studentId, Long courseId);

    Optional<Student> findByID(Long id);
}
