package anton.kaliadau.course.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.course.CourseService;
import anton.kaliadau.course.model.Course;
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

import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServletTest extends AbstractUnitTest {
    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseServlet courseServlet = new CourseServlet();

    @Test
    @SneakyThrows
    @DisplayName("Create a new course positive scenario")
    void doPost() {
        //given: new course with correct name to add to database
        var course = Course.builder().id(ID_LONG).name(courseName).students(List.of()).build();
        var expectedJson = "{\"id\":1,\"name\":\"GO For Beginners.\",\"students\":[]}";
        when(request.getParameter("name")).thenReturn(courseName);
        when(response.getWriter()).thenReturn(writer);
        when(courseService.saveCourse(courseName)).thenReturn(course);

        //when: request is sent
        courseServlet.doPost(request, response);

        //then: we have a correct jsom in response
        jsonVerification(expectedJson);
    }

    @NullSource
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("Create a new course negative scenarios")
    void doPost(String courseName) {
        //given: new course with invalid name to add to database
        when(request.getParameter(PARAMETER_NAME)).thenReturn(courseName);

        //when: request is sent
        courseServlet.doPost(request, response);

        //then: new course is not added
        verify(courseService, never()).saveCourse(courseName);
    }

    @SneakyThrows
    @Test
    void doGet() {
        //given: there are several courses in the database
        var courses = List.of(new Course(1L, "First course", List.of()),
                new Course(2L, "Second course", List.of()));
        var expectedJson = "[{\"id\":1,\"name\":\"First course\",\"students\":[]},{\"id\":2,\"name\":\"Second course\",\"students\":[]}]";
        when(courseService.findAll()).thenReturn(courses);
        when(response.getWriter()).thenReturn(writer);

        //when: request sent
        courseServlet.doGet(request, response);

        //then: correct json sent
        jsonVerification(expectedJson);
    }
}