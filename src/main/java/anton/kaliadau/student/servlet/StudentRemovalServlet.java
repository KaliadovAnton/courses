package anton.kaliadau.student.servlet;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.student.StudentService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@NoArgsConstructor
@WebServlet("/student/remove")
public class StudentRemovalServlet extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentService = BeanFactory.getStudentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Student deletion procedure started");
        try {
            var studentId = Long.parseLong(req.getParameter("id"));
            studentService.deleteById(studentId);
            resp.setStatus(200);
            var dispatcher = req.getRequestDispatcher("/WEB-INF/object-deleted.jsp");
            dispatcher.forward(req, resp);
            log.info("Student deletion procedure finished successfully");
        } catch (NumberFormatException e) {
            resp.sendError(400);
            log.info("Student deletion procedure finished with an exception");
        } catch (NoSuchElementException e) {
            resp.sendError(404);
            log.info("Student deletion procedure finished with an exception");
        }
    }
}
