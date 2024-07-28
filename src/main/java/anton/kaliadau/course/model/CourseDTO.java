package anton.kaliadau.course.model;

import anton.kaliadau.student.model.StudentDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseDTO {
    private Long id;
    private String name;
    private List<StudentDTO> students;
}
