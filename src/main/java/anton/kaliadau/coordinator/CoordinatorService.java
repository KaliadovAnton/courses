package anton.kaliadau.coordinator;

import anton.kaliadau.coordinator.model.Coordinator;

import java.util.List;

public interface CoordinatorService {
    Coordinator saveCoordinator(String coordinatorName);

    void deleteById(Long coordinatorId);

    void updateNameById(Long studentId, String newName);

    List<Coordinator> findAll();

    void deleteAll();

    Coordinator findById(Long id);
}
