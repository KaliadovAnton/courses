package anton.kaliadau.student.servlet;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.student.StudentMapper;
import anton.kaliadau.student.StudentService;
import anton.kaliadau.student.impl.StudentMapperImpl;
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

import static anton.kaliadau.util.ServletUtil.sendJson;

@Slf4j
@NoArgsConstructor
@WebServlet("/student/update")
public class StudentUpdateServlet extends HttpServlet {

    private StudentService studentService;

    private final StudentMapper studentMapper = new StudentMapperImpl();

    @Override
    public void init(ServletConfig config) {
        studentService = BeanFactory.getStudentService();
    }

    //update student by id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Student update procedure started");
        try {
            var studentId = Long.parseLong(req.getParameter("id"));
            var newName = req.getParameter("new_name");
            if (newName == null || newName.isEmpty() || newName.isBlank()) {
                log.info("Incorrect name of student. Procedure finished.");
                resp.sendError(400);
                return;
            }
            studentService.updateNameById(studentId, newName);
            var dispatcher = req.getRequestDispatcher("/WEB-INF/object-updated.jsp");
            dispatcher.forward(req, resp);
            log.info("Student update procedure finished successfully");
        } catch (NumberFormatException e) {
            resp.sendError(400);
            log.info("Student update procedure finished with an exception");
        } catch (NoSuchElementException e) {
            resp.sendError(404);
            log.info("Student update procedure finished with an exception");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Student coordinator update procedure started");
        try {
            var studentId = Long.parseLong(req.getParameter("student_id"));
            var coordinatorId = Long.parseLong(req.getParameter("coordinator_id"));
            var student = studentService.updateCoordinator(studentId, coordinatorId);
            var dto = studentMapper.studentToStudentDTO(student);
            resp.setStatus(200);
            sendJson(resp, dto);
            log.info("Student update procedure finished successfully");
        } catch (NumberFormatException e) {
            resp.sendError(400);
            log.info("Student update procedure finished with an exception");
        } catch (NoSuchElementException e) {
            resp.sendError(404);
            log.info("Student update procedure finished with an exception");
        }
    }
}
