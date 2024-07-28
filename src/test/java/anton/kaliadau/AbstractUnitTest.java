package anton.kaliadau;

import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.course.model.Course;
import anton.kaliadau.student.model.Student;
import anton.kaliadau.student.model.StudentDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class AbstractUnitTest {
    protected final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    protected final HttpServletRequest request = mock(HttpServletRequest.class);
    protected final HttpServletResponse response = mock(HttpServletResponse.class);
    protected final PrintWriter writer = mock(PrintWriter.class);
    protected final String coordinatorName = "New Coordinator";
    protected final Long coordinatorId = 1L;
    protected final Long studentId = 1L;
    protected final String courseName = "GO For Beginners.";
    protected final String studentName = "Pippa Pipkin";
    protected final Long ID_LONG = 1L;
    protected final String newName = "New Name";
    protected final String ID = "id";
    protected final String ID_STRING = "1";
    protected final String PARAMETER_NAME = "name";
    protected final String PARAMETER_COURSE_ID = "course_id";
    protected final String PARAMETER_COORDINATOR_ID = "coordinator_id";
    protected final String PARAMETER_STUDENT_ID = "student_id";
    protected final String PARAMETER_NEW_NAME = "new_name";
    protected final String PATH_TO_JSP_UPDATED = "/WEB-INF/object-updated.jsp";
    protected final String PATH_TO_JSP_DELETED = "/WEB-INF/object-deleted.jsp";
    protected final List<Student> students = List.of(new Student(1L, "Pippa Pipkin", null, null),
            new Student(2L, "Maemi Tenma", null, null));
    protected final List<StudentDTO> studentsDTO = List.of(new StudentDTO("Pippa Pipkin", 1L, null),
            new StudentDTO("Maemi Tenma", 2L, null));

    protected final Course course = new Course(1L, "a", students);
    protected final Coordinator coordinator = new Coordinator(ID_LONG, "Sakana", List.of());

    protected final Student student = new Student(ID_LONG, "Pippa Pipkin", coordinator, List.of());

    protected void jsonVerification(String expectedJson) {
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setCharacterEncoding("UTF-8");
        verify(writer, times(1)).print(expectedJson);
        verify(writer, times(1)).flush();
    }
}
