package anton.kaliadau.student.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.student.StudentService;
import anton.kaliadau.student.model.Student;
import jakarta.servlet.RequestDispatcher;
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
import java.util.NoSuchElementException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentUpdateServletTest extends AbstractUnitTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentUpdateServlet studentServlet = new StudentUpdateServlet();


    @Test
    @SneakyThrows
    @DisplayName("Update student correctly when correct id and name are passed")
    void doPut() {
        //given: correct id and name
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_NEW_NAME)).thenReturn(newName);
        doNothing().when(studentService).updateNameById(studentId, newName);
        when(request.getRequestDispatcher(PATH_TO_JSP_UPDATED)).thenReturn(dispatcher);
        //when: request is sent
        studentServlet.doGet(request, response);

        //then: student is updated
        verify(dispatcher, times(1)).forward(request, response);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "a"})
    @DisplayName("Set response status 400 when invalid id has been passed to update student request")
    void doPut(String studentId) {
        //given: invalid student id
        when(request.getParameter(ID)).thenReturn(studentId);

        //when: request sent
        studentServlet.doGet(request, response);

        //then: status is 400
        verify(response, times(1)).sendError(400);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("Set response status 400 when invalid name has been passed to update student request")
    void doPut2(String newName) {
        //given: invalid student id

        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_NEW_NAME)).thenReturn(newName);

        //when: request sent
        studentServlet.doGet(request, response);

        //then: status is 400
        verify(response, times(1)).sendError(400);
    }

    @Test
    @SneakyThrows
    @DisplayName("Set response status 404 when no student found during update")
    void doPut2() {
        //given: request with valid student id
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_NEW_NAME)).thenReturn(newName);
        doThrow(NoSuchElementException.class).when(studentService).updateNameById(ID_LONG, newName);

        //when: request sent
        studentServlet.doGet(request, response);

        //then: student deleted
        verify(response, times(1)).sendError(404);
    }


    @SneakyThrows
    @Test
    void doPost() {
        //given: request with valid student id
        var studentIdLong = 1L;
        var coordinatorIdLong = 1L;

        var expectedJson = "{\"name\":\"Pippa Pipkin\",\"id\":1,\"coordinator\":{\"id\":1,\"name\":\"Sakana\",\"studentIds\":[]}}";

        when(request.getParameter(PARAMETER_STUDENT_ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_COORDINATOR_ID)).thenReturn(ID_STRING);
        when(studentService.updateCoordinator(studentIdLong, coordinatorIdLong)).thenReturn(student);
        when(response.getWriter()).thenReturn(writer);

        //when: updating student's coordinator
        studentServlet.doPost(request, response);

        //then: response is sent
        jsonVerification(expectedJson);
    }
}