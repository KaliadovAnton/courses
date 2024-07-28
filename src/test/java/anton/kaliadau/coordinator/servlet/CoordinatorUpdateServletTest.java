package anton.kaliadau.coordinator.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.coordinator.CoordinatorService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoordinatorUpdateServletTest extends AbstractUnitTest {
    @Mock
    private CoordinatorService coordinatorService;

    @InjectMocks
    private CoordinatorUpdateServlet coordinatorUpdateServlet = new CoordinatorUpdateServlet();

    @Test
    @DisplayName("Update student correctly when correct id and name are passed")
    void doPut() throws IOException, ServletException {
        //given: correct id and name
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("new_name")).thenReturn(newName);
        when(request.getRequestDispatcher("/WEB-INF/object-updated.jsp")).thenReturn(dispatcher);
        doNothing().when(coordinatorService).updateNameById(studentId, newName);
        //when: request is sent
        coordinatorUpdateServlet.doGet(request, null);
        //then: student is updated
        verify(dispatcher, times(1)).forward(request, null);
    }
}