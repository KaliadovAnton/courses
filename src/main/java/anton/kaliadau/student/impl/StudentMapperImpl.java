package anton.kaliadau.student.impl;

import anton.kaliadau.coordinator.CoordinatorMapper;
import anton.kaliadau.coordinator.impl.CoordinatorMapperImpl;
import anton.kaliadau.student.model.Student;
import anton.kaliadau.student.model.StudentDTO;
import anton.kaliadau.student.StudentMapper;

public class StudentMapperImpl implements StudentMapper {

    private final CoordinatorMapper coordinatorMapper = new CoordinatorMapperImpl();
    @Override
    public Student studentDTOtoStudent(StudentDTO studentDto) {
        return Student.builder()
                .id(studentDto.getId())
                .name(studentDto.getName())
                .build();
    }

    @Override
    public StudentDTO studentToStudentDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .coordinator(coordinatorMapper.coordinatorToCoordinatorDTO(student.getCoordinator()))
                .build();
    }
}
