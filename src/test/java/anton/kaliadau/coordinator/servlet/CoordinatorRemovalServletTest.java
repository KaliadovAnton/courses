package anton.kaliadau.coordinator.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.coordinator.CoordinatorService;
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
class CoordinatorRemovalServletTest extends AbstractUnitTest {
    @Mock
    private CoordinatorService coordinatorService;

    @InjectMocks
    private CoordinatorRemovalServlet coordinatorRemovalServlet = new CoordinatorRemovalServlet();

    @Test
    @SneakyThrows
    void doDelete() {
        //given: request with valid coordinator id
        var coordinatorId = "1";
        when(request.getParameter("id")).thenReturn(coordinatorId);
        doNothing().when(coordinatorService).deleteById(1L);
        doNothing().when(response).setStatus(200);
        when(request.getRequestDispatcher("/WEB-INF/object-deleted.jsp")).thenReturn(dispatcher);

        //when: request sent
        coordinatorRemovalServlet.doGet(request, response);

        //then: coordinator deleted
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    @SneakyThrows
    @DisplayName("Set response status 404 when no coordinator found")
    void doDelete2() {
        //given: request with valid student id
        var coordinatorId = "1";
        when(request.getParameter("id")).thenReturn(coordinatorId);
        doThrow(NoSuchElementException.class).when(coordinatorService).deleteById(1L);

        //when: request sent
        coordinatorRemovalServlet.doGet(request, response);

        //then: student deleted
        verify(response, times(1)).sendError(404);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "a"})
    @DisplayName("Set response status 400 when invalid id has been passed to request")
    void doDelete(String coordinatorId) {
        //given: invalid student id
        when(request.getParameter("id")).thenReturn(coordinatorId);
        //When: request sent
        coordinatorRemovalServlet.doGet(request, response);
        //Then: status is 400
        verify(response, times(1)).sendError(400);
    }
}