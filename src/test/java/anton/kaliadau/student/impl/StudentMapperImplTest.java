package anton.kaliadau.student.impl;

import anton.kaliadau.student.model.Student;
import anton.kaliadau.student.model.StudentDTO;
import anton.kaliadau.student.StudentMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperImplTest {

    private final StudentMapper studentMapper = new StudentMapperImpl();

    @Test
    void studentDTOtoStudent() {
        //given: student dto
        var studentDto = StudentDTO.builder().id(1L).name("name").build();
        //when: dto mapped to student
        var result = studentMapper.studentDTOtoStudent(studentDto);
        //then: student mapped correctly
        assertEquals(studentDto.getId(), result.getId());
        assertEquals(studentDto.getName(), result.getName());
    }

    @Test
    void studentToStudentDTO() {
        //given: student
        var student = Student.builder().id(1L).name("name").build();
        //when: student mapped to dto
        var result = studentMapper.studentToStudentDTO(student);
        //then: dto mapped correctly
        assertEquals(student.getId(), result.getId());
        assertEquals(student.getName(), result.getName());
    }
}