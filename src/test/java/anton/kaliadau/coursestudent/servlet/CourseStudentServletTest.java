package anton.kaliadau.coursestudent.servlet;

import anton.kaliadau.AbstractUnitTest;
import anton.kaliadau.coursestudent.CourseStudentService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseStudentServletTest extends AbstractUnitTest {

    @Mock
    private CourseStudentService courseStudentService;

    @InjectMocks
    private CourseStudentServlet courseStudentServlet = new CourseStudentServlet();

    @SneakyThrows
    @Test
    void doGet() {
        //given: there are a student and a course
        when(request.getParameter(PARAMETER_COURSE_ID)).thenReturn(ID_STRING);
        when(request.getParameter(PARAMETER_STUDENT_ID)).thenReturn(ID_STRING);
        doNothing().when(courseStudentService).addCourseToStudent(Long.parseLong(ID_STRING), Long.parseLong(ID_STRING));
        when(request.getRequestDispatcher(PATH_TO_JSP_UPDATED)).thenReturn(dispatcher);
        //when: request to set course for student sent
        courseStudentServlet.doPost(request, response);
        //then: student updated
        verify(dispatcher, times(1)).forward(request, response);
    }

}