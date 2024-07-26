package anton.kaliadau.coordinator.impl;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.coordinator.CoordinatorRepository;
import anton.kaliadau.coordinator.CoordinatorService;

import java.util.List;

public class CoordinatorServiceImpl implements CoordinatorService {

    private final CoordinatorRepository repository;

    public CoordinatorServiceImpl() {
        this.repository = BeanFactory.getCoordinatorRepository();
    }

    @Override
    public Coordinator saveCoordinator(String coordinatorName) {
        return repository.save(coordinatorName);
    }

    @Override
    public void deleteById(Long coordinatorId) {
        repository.deleteById(coordinatorId);
    }

    @Override
    public void updateNameById(Long coordinatorId, String newName) {
        repository.updateNameById(coordinatorId, newName);
    }

    @Override
    public List<Coordinator> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Coordinator findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
