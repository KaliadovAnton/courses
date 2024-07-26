package anton.kaliadau.course.servlet;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.course.CourseMapper;
import anton.kaliadau.course.CourseService;
import anton.kaliadau.course.impl.CourseMapperImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.stream.Collectors;

import static anton.kaliadau.util.ServletUtil.sendJson;

@Slf4j
@NoArgsConstructor
@WebServlet(urlPatterns = "/course")
public class CourseServlet extends HttpServlet {

    private CourseService courseService;
    private final CourseMapper courseMapper = new CourseMapperImpl();

    @Override
    public void init(ServletConfig config) {
        courseService = BeanFactory.getCourseService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Add a new course procedure started");
        var courseName = req.getParameter("name");
        if (courseName == null || courseName.isBlank() || courseName.isEmpty()) {
            log.info("Incorrect name of course. Procedure finished.");
            return;
        }
        var course = courseService.saveCourse(courseName);
        var dto = courseMapper.courseToCourseDTO(course);
        sendJson(resp, dto);
        log.info("Add a new course procedure finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Getting information about all courses started");
        var courses = courseService.findAll().stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
        sendJson(resp, courses);
        log.info("Getting information about all courses finished");
    }
}
