package anton.kaliadau.coursestudent.servlet;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.coursestudent.CourseStudentService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@NoArgsConstructor
@WebServlet(urlPatterns = "/course/add")
public class CourseStudentServlet extends HttpServlet {

    private CourseStudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.studentService = BeanFactory.getCourseStudentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Add a course to student procedure started");
        var courseId = Long.valueOf(req.getParameter("course_id"));
        var studentId =  Long.valueOf(req.getParameter("student_id"));
        studentService.addCourseToStudent(studentId, courseId);
        var dispatcher = req.getRequestDispatcher("/WEB-INF/object-updated.jsp");
        dispatcher.forward(req, resp);
        log.info("Add a course to a student procedure finished");
    }
}
