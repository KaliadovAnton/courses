package anton.kaliadau.course.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.course.CourseService;
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

import java.util.NoSuchElementException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseUpdateServletTest extends AbstractUnitTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseUpdateServlet courseServlet = new CourseUpdateServlet();

    @Test
    @SneakyThrows
    @DisplayName("Update course correctly when correct id and name are passed")
    void doPut() {
        //given: correct id and name
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_NEW_NAME)).thenReturn(newName);
        doNothing().when(courseService).updateNameById(ID_LONG, newName);
        when(request.getRequestDispatcher(PATH_TO_JSP_UPDATED)).thenReturn(dispatcher);

        //when: request is sent
        courseServlet.doGet(request, response);

        //then: student is updated
        verify(dispatcher, times(1)).forward(request, response);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "a"})
    @DisplayName("Set response status 400 when invalid id has been passed to update course request")
    void doPut(String courseId) {
        //given: invalid course id
        when(request.getParameter(ID)).thenReturn(courseId);

        //When: request sent
        courseServlet.doGet(request, response);

        //Then: status is 400
        verify(response, times(1)).sendError(400);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("Set response status 400 when invalid name has been passed to update course request")
    void doPut2(String courseName) {
        //given: invalid student id
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_NEW_NAME)).thenReturn(courseName);

        //When: request sent
        courseServlet.doGet(request, response);

        //Then: status is 400
        verify(response, times(1)).sendError(400);
    }

    @Test
    @SneakyThrows
    @DisplayName("Set response status 404 when no course found during update")
    void doPut2() {
        //given: request with valid student id
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_NEW_NAME)).thenReturn(newName);
        doThrow(NoSuchElementException.class).when(courseService).updateNameById(ID_LONG, newName);

        //when: request sent
        courseServlet.doGet(request, response);

        //then: student deleted
        verify(response, times(1)).sendError(404);
    }
}