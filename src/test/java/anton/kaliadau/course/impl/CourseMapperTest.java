package anton.kaliadau.course.impl;


import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.course.CourseMapper;
import anton.kaliadau.course.model.Course;
import anton.kaliadau.course.model.CourseDTO;
import anton.kaliadau.student.StudentMapper;
import anton.kaliadau.student.impl.StudentMapperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseMapperTest extends AbstractUnitTest {

    private final CourseMapper courseMapper = new CourseMapperImpl();
    private final StudentMapper studentMapper = new StudentMapperImpl();

    @Test
    public void courseToCourseDTO() {
        //given: valid course
        var expected = CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .students(course.getStudents().stream()
                        .map(studentMapper::studentToStudentDTO)
                        .toList())
                .build();
        //when: mapped to dto
        var result = courseMapper.courseToCourseDTO(course);

        //then: fields are equal
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getStudents(), result.getStudents());
    }

    @Test
    public void courseDTOtoCourse() {
        //given: valid courseDTO
        var courseDTO = CourseDTO.builder().id(ID_LONG).name(newName).students(studentsDTO).build();
        var expected = new Course(courseDTO.getId(), courseDTO.getName(), courseDTO.getStudents().stream()
                .map(studentMapper::studentDTOtoStudent)
                .toList());

        //when: mapped to entity
        var result = courseMapper.courseDTOtoCourse(courseDTO);

        //then: fields are equal
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getStudents(), result.getStudents());
    }
}