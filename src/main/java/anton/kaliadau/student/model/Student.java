package anton.kaliadau.student.model;

import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.course.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Student {
    private Long id;
    private String name;
    private Coordinator coordinator;
    private List<Course> courses;
}
