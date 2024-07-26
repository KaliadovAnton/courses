package anton.kaliadau.course.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.course.CourseService;
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

import java.util.NoSuchElementException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseRemovalServletTest extends AbstractUnitTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseRemovalServlet courseServlet = new CourseRemovalServlet();


    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "a"})
    @DisplayName("Set response status 400 when invalid id has been passed to request")
    void doDelete(String courseId) {
        //given: invalid course id
        when(request.getParameter(ID)).thenReturn(courseId);
        //When: request sent
        courseServlet.doGet(request, response);
        //Then: status is 400
        verify(response, times(1)).sendError(400);
    }

    @Test
    @SneakyThrows
    void doDelete() {
        //given: request with valid course id
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        doNothing().when(courseService).deleteById(ID_LONG);
        doNothing().when(response).setStatus(200);
        when(request.getRequestDispatcher(PATH_TO_JSP_DELETED)).thenReturn(dispatcher);

        //when: request sent
        courseServlet.doGet(request, response);

        //then: course deleted
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    @SneakyThrows
    @DisplayName("Set response status 404 when no course found")
    void doDelete2() {
        //given: request with valid student id
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        doThrow(NoSuchElementException.class).when(courseService).deleteById(ID_LONG);

        //when: request sent
        courseServlet.doGet(request, response);

        //then: student deleted
        verify(response, times(1)).sendError(404);
    }
}