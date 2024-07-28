package anton.kaliadau.coordinator;

import anton.kaliadau.coordinator.model.Coordinator;

import java.util.List;
import java.util.Optional;

public interface CoordinatorRepository {
    Coordinator save(String coordinatorName);

    void deleteById(Long coordinatorId);

    void updateNameById(Long coordinatorId, String newName);

    List<Coordinator> findAll();

    void deleteAll();

    Optional<Coordinator> findById(Long id);
}
