package anton.kaliadau.student.model;

import anton.kaliadau.coordinator.model.CoordinatorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StudentDTO {
    private final String name;
    private final Long id;
    private final CoordinatorDTO coordinator;
}
