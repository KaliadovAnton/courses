package anton.kaliadau.course;

import anton.kaliadau.course.model.Course;
import anton.kaliadau.course.model.CourseDTO;

public interface CourseMapper {
    Course courseDTOtoCourse(CourseDTO dto);

    CourseDTO courseToCourseDTO(Course course);
}
