package anton.kaliadau.student.servlet;

import anton.kaliadau.student.StudentMapper;
import anton.kaliadau.student.StudentService;
import anton.kaliadau.student.impl.StudentMapperImpl;
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
@WebServlet("/student/acquire")
public class StudentAcquireServlet extends HttpServlet {
    private StudentService studentService;
    private final StudentMapper studentMapper = new StudentMapperImpl();

    @Override
    public void init(ServletConfig config) {
        studentService = BeanFactory.getStudentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var studentId = req.getParameter("id");
        log.info("Getting information about student with id {}. Procedure started", studentId);
        var student = studentService.findById(Long.parseLong(studentId));
        var studentDto = studentMapper.studentToStudentDTO(student);
        sendJson(resp, studentDto);
        log.info("Getting information about student with id {}. Procedure finished", studentId);
    }
}
