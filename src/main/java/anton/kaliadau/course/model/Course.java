package anton.kaliadau.course.model;

import anton.kaliadau.student.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Course {
    private Long id;
    private String name;
    private List<Student> students;
}
