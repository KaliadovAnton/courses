package anton.kaliadau.coordinator.model;

import anton.kaliadau.student.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Coordinator {
    private Long id;
    private String name;
    private List<Student> students;
}
