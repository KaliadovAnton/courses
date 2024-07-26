package anton.kaliadau.coursestudent.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseStudent {
    private Long courseId;
    private Long studentId;
}
