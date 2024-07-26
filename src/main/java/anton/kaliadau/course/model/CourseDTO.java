package anton.kaliadau.course.model;

import anton.kaliadau.student.model.Student;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseDTO {
    private Long id;
    private String name;
    //TODO: change to StudentDTO
    private List<Student> students;
}
