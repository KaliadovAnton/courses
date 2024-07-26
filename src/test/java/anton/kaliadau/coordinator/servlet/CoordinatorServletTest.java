package anton.kaliadau.coordinator.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.coordinator.CoordinatorService;
import anton.kaliadau.coordinator.model.Coordinator;
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
class CoordinatorServletTest extends AbstractUnitTest {

    @Mock
    private CoordinatorService coordinatorService;

    @InjectMocks
    private CoordinatorServlet coordinatorServlet = new CoordinatorServlet();

    @Test
    @SneakyThrows
    @DisplayName("Create a new student positive scenario")
    void doPost() {
        //given: new course with correct name to add to database
        var student = Coordinator.builder().id(coordinatorId).name(coordinatorName).students(List.of()).build();
        var expectedJson = "{\"id\":1,\"name\":\"New Coordinator\",\"studentIds\":[]}";
        when(request.getParameter("name")).thenReturn(coordinatorName);
        when(response.getWriter()).thenReturn(writer);
        when(coordinatorService.saveCoordinator(coordinatorName)).thenReturn(student);

        //when: request is sent
        coordinatorServlet.doPost(request, response);

        //then: we have a correct jsom in response
        jsonVerification(expectedJson);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("Create a new coordinator negative scenarios")
    void doPost(String coordinatorName) {
        //given: new course with invalid name to add to database
        when(request.getParameter("name")).thenReturn(coordinatorName);

        //when: request is sent
        coordinatorServlet.doPost(request, response);

        //then: new course is not added
        verify(coordinatorService, never()).saveCoordinator(coordinatorName);
    }

    @SneakyThrows
    @Test
    void doGet() {
        //given: there are students
        var students = List.of(new Coordinator(1L, "Pippa Pipkin", List.of()),
                new Coordinator(2L, "Maemi Tenma", List.of()));
        var expectedJson = "[{\"id\":1,\"name\":\"Pippa Pipkin\",\"studentIds\":[]},{\"id\":2,\"name\":\"Maemi Tenma\",\"studentIds\":[]}]";
        when(coordinatorService.findAll()).thenReturn(students);
        when(response.getWriter()).thenReturn(writer);
        //when: request sent
        coordinatorServlet.doGet(request, response);
        //then: correct json sent
        jsonVerification(expectedJson);
    }
}