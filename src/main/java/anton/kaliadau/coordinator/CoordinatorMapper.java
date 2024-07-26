package anton.kaliadau.coordinator;

import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.coordinator.model.CoordinatorDTO;

public interface CoordinatorMapper {
    CoordinatorDTO coordinatorToCoordinatorDTO(Coordinator coordinator);
}
