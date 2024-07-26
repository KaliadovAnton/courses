package anton.kaliadau.coordinator.servlet;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.coordinator.CoordinatorMapper;
import anton.kaliadau.coordinator.CoordinatorService;
import anton.kaliadau.coordinator.impl.CoordinatorMapperImpl;
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
@WebServlet(urlPatterns = "/coordinator")
public class CoordinatorServlet extends HttpServlet {

    private CoordinatorService coordinatorService;

    private final CoordinatorMapper coordinatorMapper = new CoordinatorMapperImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.coordinatorService = BeanFactory.getCoordinatorService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("Add a new coordinator procedure started");
        var coordinatorName = req.getParameter("name");
        if (coordinatorName == null || coordinatorName.isBlank() || coordinatorName.isEmpty()) {
            log.info("Incorrect name of coordinator. Procedure finished.");
            resp.sendError(400);
            return;
        }
        var coordinator = coordinatorService.saveCoordinator(coordinatorName);
        var dto = coordinatorMapper.coordinatorToCoordinatorDTO(coordinator);
        sendJson(resp, dto);
        log.info("Add a new coordinator procedure finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Getting information about all coordinators started");
        var students = coordinatorService.findAll().stream()
                .map(coordinatorMapper::coordinatorToCoordinatorDTO)
                .collect(Collectors.toList());
        sendJson(resp, students);
        log.info("Getting information about all coordinators finished");
    }
}
