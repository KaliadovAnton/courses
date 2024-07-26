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
import java.util.stream.Collectors;

import static anton.kaliadau.util.ServletUtil.sendJson;

@Slf4j
@NoArgsConstructor
@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private StudentService studentService;
    private final StudentMapper studentMapper = new StudentMapperImpl();

    @Override
    public void init(ServletConfig config) {
        studentService = BeanFactory.getStudentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("Add a new student procedure started");
        var studentName = req.getParameter("name");
        if (studentName == null || studentName.isBlank() || studentName.isEmpty()) {
            log.info("Incorrect name of student. Procedure finished.");
            resp.sendError(400);
            return;
        }
        var student = studentService.saveStudent(studentName);
        var dto = studentMapper.studentToStudentDTO(student);
        sendJson(resp, dto);
        log.info("Add a new student procedure finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Getting information about all students started");
        var students = studentService.findAll().stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
        sendJson(resp, students);
        log.info("Getting information about all students finished");
    }
}
