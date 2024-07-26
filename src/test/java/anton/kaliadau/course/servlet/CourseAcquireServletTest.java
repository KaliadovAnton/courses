package anton.kaliadau.course.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.course.CourseService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseAcquireServletTest extends AbstractUnitTest {
    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseAcquireServlet studentServlet = new CourseAcquireServlet();

    @Test
    @SneakyThrows
    void doGet() {
        //given: request with valid student id
        var expectedJson = "{\"id\":1,\"name\":\"a\",\"students\":[{\"id\":1,\"name\":\"Pippa Pipkin\"},{\"id\":2,\"name\":\"Maemi Tenma\"}]}";
        when(request.getParameter(ID)).thenReturn(ID_STRING);
        when(courseService.findById(ID_LONG)).thenReturn(course);
        when(response.getWriter()).thenReturn(writer);

        //when: request sent
        studentServlet.doGet(request, response);

        //then: student deleted
        jsonVerification(expectedJson);
    }
}