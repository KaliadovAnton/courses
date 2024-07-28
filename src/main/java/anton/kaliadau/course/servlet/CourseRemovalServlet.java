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
@WebServlet("/course/remove")
public class CourseRemovalServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init(ServletConfig config) {
        courseService = BeanFactory.getCourseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Course deletion procedure started");
        try {
            var courseId = Long.parseLong(req.getParameter("id"));
            courseService.deleteById(courseId);
            resp.setStatus(200);
            var dispatcher = req.getRequestDispatcher("/WEB-INF/object-deleted.jsp");
            dispatcher.forward(req, resp);
            log.info("Course deletion procedure finished successfully");
        } catch (NumberFormatException e) {
            resp.sendError(400);
            log.info("Course deletion procedure finished with an exception");
        } catch (NoSuchElementException e) {
            resp.sendError(404);
            log.info("Course deletion procedure finished with an exception");
        }
    }
}
