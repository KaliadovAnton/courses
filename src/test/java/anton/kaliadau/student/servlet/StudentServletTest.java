package anton.kaliadau.student.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.student.StudentService;
import anton.kaliadau.student.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServletTest extends AbstractUnitTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentServlet studentServlet = new StudentServlet();

    @Test
    @SneakyThrows
    @DisplayName("Create a new student positive scenario")
    void doPost() {
        //given: new course with correct name to add to database
        var student = Student.builder().id(studentId).name(studentName).build();
        var expectedJson = "{\"name\":\"Pippa Pipkin\",\"id\":1,\"coordinator\":{}}";
        when(request.getParameter("name")).thenReturn(studentName);
        when(response.getWriter()).thenReturn(writer);
        when(studentService.saveStudent(studentName)).thenReturn(student);

        //when: request is sent
        studentServlet.doPost(request, response);

        //then: we have a correct jsom in response
        jsonVerification(expectedJson);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("Create a new student negative scenarios")
    void doPost(String studentName) {
        //given: new course with invalid name to add to database
        when(request.getParameter(PARAMETER_NAME)).thenReturn(studentName);

        //when: request is sent
        studentServlet.doPost(request, response);

        //then: new course is not added
        verify(studentService, never()).saveStudent(studentName);
    }

    @SneakyThrows
    @Test
    void doGet() {
        //given: there are students
        var students = List.of(new Student(1L, "Pippa Pipkin", null, null),
                new Student(2L, "Maemi Tenma", null, null));
        var expectedJson = "[{\"name\":\"Pippa Pipkin\",\"id\":1,\"coordinator\":{}},{\"name\":\"Maemi Tenma\",\"id\":2,\"coordinator\":{}}]";
        when(studentService.findAll()).thenReturn(students);
        when(response.getWriter()).thenReturn(writer);
        //when: request sent
        studentServlet.doGet(request, response);
        //then: correct json sent
        jsonVerification(expectedJson);
    }
}