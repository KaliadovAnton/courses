package anton.kaliadau.student;

import anton.kaliadau.student.model.Student;

import java.util.List;

public interface StudentService {
    Student saveStudent(String studentName);

    void deleteById(Long studentId);

    void updateNameById(Long studentId, String newName);

    List<Student> findAll();

    void deleteAll();

    Student updateCoordinator(Long studentId, Long coordinatorId);

    Student findById(Long id);
}
