package anton.kaliadau.course.impl;

import anton.kaliadau.course.model.Course;
import anton.kaliadau.course.model.CourseDTO;
import anton.kaliadau.course.CourseMapper;

public class CourseMapperImpl implements CourseMapper {

    @Override
    public Course courseDTOtoCourse(CourseDTO dto) {
        return Course.builder()
                .id(dto.getId())
                .name(dto.getName())
                .students(dto.getStudents())
                .build();
    }

    @Override
    public CourseDTO courseToCourseDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .students(course.getStudents())
                .build();
    }
}
