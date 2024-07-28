package anton.kaliadau.coordinator.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.coordinator.CoordinatorService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoordinatorAcquireServletTest extends AbstractUnitTest {
    @Mock
    private CoordinatorService coordinatorService;

    @InjectMocks
    private CoordinatorAcquireServlet studentServlet = new CoordinatorAcquireServlet();

    @Test
    @SneakyThrows
    void doGet() {
        //given: request with valid student id
        var expectedJson = "{\"id\":1,\"name\":\"Sakana\",\"studentIds\":[]}";
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(coordinatorService.findById(ID_LONG)).thenReturn(coordinator);
        when(response.getWriter()).thenReturn(writer);

        //when: request sent
        studentServlet.doGet(request, response);

        //then: student deleted
        jsonVerification(expectedJson);
    }
}