package anton.kaliadau.student;

import anton.kaliadau.student.model.Student;
import anton.kaliadau.student.model.StudentDTO;

public interface StudentMapper {

    Student studentDTOtoStudent(StudentDTO studentDto);
    StudentDTO studentToStudentDTO(Student student);
}
