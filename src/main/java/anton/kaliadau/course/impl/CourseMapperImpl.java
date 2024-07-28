package anton.kaliadau.course.impl;

import anton.kaliadau.course.model.Course;
import anton.kaliadau.course.model.CourseDTO;
import anton.kaliadau.course.CourseMapper;
import anton.kaliadau.student.StudentMapper;
import anton.kaliadau.student.impl.StudentMapperImpl;

public class CourseMapperImpl implements CourseMapper {

    private final StudentMapper studentMapper = new StudentMapperImpl();

    @Override
    public Course courseDTOtoCourse(CourseDTO dto) {
        return Course.builder()
                .id(dto.getId())
                .name(dto.getName())
                .students(dto.getStudents().stream()
                        .map(studentMapper::studentDTOtoStudent)
                        .toList())
                .build();
    }

    @Override
    public CourseDTO courseToCourseDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .students(course.getStudents().stream()
                        .map(studentMapper::studentToStudentDTO)
                        .toList())
                .build();
    }
}
