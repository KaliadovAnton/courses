package anton.kaliadau.student.impl;

import anton.kaliadau.coordinator.impl.CoordinatorServiceImpl;
import anton.kaliadau.AbstractIntegrationTest;
import anton.kaliadau.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest extends AbstractIntegrationTest {

    private final StudentServiceImpl studentService = new StudentServiceImpl();
    private final CoordinatorServiceImpl coordinatorService = new CoordinatorServiceImpl();

    @BeforeEach
    void cleanup() {
        studentService.deleteAll();
    }

    @Test
    void findAll() {
        //given: several students
        studentService.saveStudent(studentName1);
        studentService.saveStudent(studentName2);
        //when: trying to get list of students
        var result = studentService.findAll();
        //then: get every known student
        assertEquals(2, result.size());

        assertTrue(result.stream().map(Student::getName).toList().contains(studentName1));
        assertTrue(result.stream().map(Student::getName).toList().contains(studentName2));
    }

    @Test
    void updateNameById() {
        //given: repository passed student with changed name
        studentService.saveStudent(studentName1);
        //when: updating student
        studentService.updateNameById(id, newName);
        var result = studentService.findById(id);
        //then: result is expected
        assertEquals(newName, result.getName());
    }

    @Test
    void saveStudent() {
        //given: student name to save
        //when: saving student
        var result = studentService.saveStudent(studentName1);
        //then: student is saved
        assertEquals(studentName1, result.getName());
    }

    @Test
    void deleteById() {
        //given: student to delete
        var givenStudent = studentService.saveStudent(studentName1);
        //when: deleting student
        studentService.deleteById(givenStudent.getId());
        //then: student is deleted
        assertFalse(studentService.findAll().stream().anyMatch(student -> student.getName().equals(studentName1)));
    }

    @Test
    void updateCoordinator() {
        //given: existing student and coordinator
        var student = studentService.saveStudent(studentName1);
        var coordinator = coordinatorService.saveCoordinator(coordinatorName1);

        //when: coordinator is assigned to student
        studentService.updateCoordinator(student.getId(), coordinator.getId());
        var result = studentService.findAll().get(0).getCoordinator().getId();

        //then: coordinator assigned
        assertEquals(coordinator.getId(), result);
    }
}