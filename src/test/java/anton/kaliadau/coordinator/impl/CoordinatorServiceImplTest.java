package anton.kaliadau.coordinator.impl;

import anton.kaliadau.AbstractIntegrationTest;
import anton.kaliadau.student.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CoordinatorServiceImplTest extends AbstractIntegrationTest {

    private final CoordinatorServiceImpl coordinatorService = new CoordinatorServiceImpl();
    private final StudentServiceImpl studentService = new StudentServiceImpl();

    @BeforeEach
    void cleanup() {
        coordinatorService.deleteAll();
    }

    @Test
    void findAll() {
        //given: several coordinators exists
        coordinatorService.saveCoordinator(coordinatorName1);
        coordinatorService.saveCoordinator(coordinatorName2);
        //when: trying to get list of students
        var result = coordinatorService.findAll();
        //then: get every known student
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(coordinator -> coordinator.getName().equals(coordinatorName1)));
        assertTrue(result.stream().anyMatch(coordinator -> coordinator.getName().equals(coordinatorName2)));
    }

    @Test
    void findAll2() {
        //given: several coordinators with students exist
        var coordinator1 = coordinatorService.saveCoordinator(coordinatorName1);
        coordinatorService.saveCoordinator(coordinatorName2);
        var student1 = studentService.saveStudent(studentName1);
        var student2 = studentService.saveStudent(studentName2);
        studentService.updateCoordinator(student1.getId(), coordinator1.getId());
        studentService.updateCoordinator(student2.getId(), coordinator1.getId());
        //when: trying to get list of students
        var result = coordinatorService.findAll();
        var coordinatorWithStudents = result.stream()
                .filter(coordinator -> coordinator.getId().equals(coordinator1.getId()))
                .toList()
                .get(0);
        //then: get every known student
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(coordinator -> coordinator.getName().equals(coordinatorName1)));
        assertTrue(result.stream().anyMatch(coordinator -> coordinator.getName().equals(coordinatorName2)));
        assertTrue(coordinatorWithStudents.getStudents().stream().anyMatch(student -> student.getName().equals(studentName1)));
        assertTrue(coordinatorWithStudents.getStudents().stream().anyMatch(student -> student.getName().equals(studentName2)));
    }

    @Test
    void updateNameById() {
        //given: repository passed student with changed name
        coordinatorService.saveCoordinator(coordinatorName1);
        //when: updating student
        coordinatorService.updateNameById(id, newName);
        var result = coordinatorService.findById(id);
        //then: result is expected
        assertEquals(newName, result.getName());
    }

    @Test
    void saveStudent() {
        //given: coordinator name to save
        //when: saving coordinator
        var result = coordinatorService.saveCoordinator(coordinatorName1);
        //then: coordinator is saved
        assertEquals(coordinatorName1, result.getName());
    }

    @Test
    void deleteById() {
        //given: coordinator to delete
        var coordinator = coordinatorService.saveCoordinator(coordinatorName1);
        //when: deleting coordinator
        coordinatorService.deleteById(coordinator.getId());
        //then: coordinator is deleted
        assertFalse(coordinatorService.findAll().stream()
                .anyMatch(existtingCoordinator -> existtingCoordinator.getName().equals(coordinatorName1)));
    }
}