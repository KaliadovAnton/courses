package anton.kaliadau.coordinator.impl;

import anton.kaliadau.coordinator.CoordinatorMapper;
import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.coordinator.model.CoordinatorDTO;
import anton.kaliadau.student.model.Student;

import java.util.List;
import java.util.stream.Collectors;

public class CoordinatorMapperImpl implements CoordinatorMapper {

    @Override
    public CoordinatorDTO coordinatorToCoordinatorDTO(Coordinator coordinator) {
        if (coordinator == null) {
            return CoordinatorDTO.builder().build();
        }
        return CoordinatorDTO.builder()
                .id(coordinator.getId())
                .name(coordinator.getName())
                .studentIds(getStudentsId(coordinator))
                .build();
    }

    private List<Long> getStudentsId(Coordinator coordinator) {
        var students = coordinator.getStudents();
        if (coordinator.getStudents() == null) {
            return List.of();
        }
        return students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
    }
}
