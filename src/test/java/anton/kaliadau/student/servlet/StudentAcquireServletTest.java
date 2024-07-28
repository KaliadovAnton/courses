package anton.kaliadau.student.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.student.StudentService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentAcquireServletTest extends AbstractUnitTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentAcquireServlet studentServlet = new StudentAcquireServlet();

    @Test
    @SneakyThrows
    void doGet() {
        //given: request with valid student id
        var expectedJson = "{\"name\":\"Pippa Pipkin\",\"id\":1,\"coordinator\":{\"id\":1,\"name\":\"Sakana\",\"studentIds\":[]}}";
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(studentService.findById(ID_LONG)).thenReturn(student);
        when(response.getWriter()).thenReturn(writer);

        //when: request sent
        studentServlet.doGet(request, response);

        //then: student deleted
        jsonVerification(expectedJson);
    }
}