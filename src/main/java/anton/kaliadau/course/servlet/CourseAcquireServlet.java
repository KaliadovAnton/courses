package anton.kaliadau.course.servlet;

import anton.kaliadau.course.CourseMapper;
import anton.kaliadau.course.CourseService;
import anton.kaliadau.course.impl.CourseMapperImpl;
import anton.kaliadau.util.BeanFactory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static anton.kaliadau.util.ServletUtil.sendJson;

@Slf4j
@NoArgsConstructor
@WebServlet("/course/acquire")
public class CourseAcquireServlet extends HttpServlet {

    private CourseService courseService;
    private final CourseMapper courseMapper = new CourseMapperImpl();

    @Override
    public void init(ServletConfig config) {
        courseService = BeanFactory.getCourseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var coordinatorId = req.getParameter("id");
        log.info("Getting information about course with id {}. Procedure started", coordinatorId);
        var course = courseService.findById(Long.parseLong(coordinatorId));
        var courseDto = courseMapper.courseToCourseDTO(course);
        sendJson(resp, courseDto);
        log.info("Getting information about course with id {}. Procedure finished", coordinatorId);
    }
}
