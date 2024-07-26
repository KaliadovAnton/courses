package anton.kaliadau.coordinator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CoordinatorDTO {
    private final Long id;
    private final String name;
    private final List<Long> studentIds;
}
