package anton.kaliadau.course.servlet;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.course.CourseService;
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
@WebServlet(urlPatterns = "/course/update")
public class CourseUpdateServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        courseService = BeanFactory.getCourseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Course update procedure started");
        try {
            var courseId = Long.parseLong(req.getParameter("id"));
            var newName = req.getParameter("new_name");
            if (newName == null || newName.isEmpty() || newName.isBlank()) {
                log.info("Incorrect name of course. Procedure finished.");
                resp.sendError(400);
                return;
            }
            courseService.updateNameById(courseId, newName);
            resp.setStatus(200);
            var dispatcher = req.getRequestDispatcher("/WEB-INF/object-updated.jsp");
            dispatcher.forward(req, resp);
            log.info("Course update procedure finished successfully");
        } catch (NumberFormatException e) {
            resp.sendError(400);
            log.info("Course update procedure finished with an exception");
        } catch (NoSuchElementException e) {
            resp.sendError(404);
            log.info("Course update procedure finished with an exception");
        }
    }
}
