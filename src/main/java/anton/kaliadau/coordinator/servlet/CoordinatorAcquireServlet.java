package anton.kaliadau.coordinator.servlet;

import anton.kaliadau.coordinator.CoordinatorMapper;
import anton.kaliadau.coordinator.CoordinatorService;
import anton.kaliadau.coordinator.impl.CoordinatorMapperImpl;
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
@WebServlet("/coordinator/acquire")
public class CoordinatorAcquireServlet extends HttpServlet {

    private CoordinatorService coordinatorService;

    private final CoordinatorMapper coordinatorMapper = new CoordinatorMapperImpl();

    @Override
    public void init(ServletConfig config) {
        coordinatorService = BeanFactory.getCoordinatorService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var coordinatorId = req.getParameter("id");
        log.info("Getting information about coordinator with id {}. Procedure started", coordinatorId);
        var coordinator = coordinatorService.findById(Long.parseLong(coordinatorId));
        var coordinatorDto = coordinatorMapper.coordinatorToCoordinatorDTO(coordinator);
        sendJson(resp, coordinatorDto);
        log.info("Getting information about coordinator with id {}. Procedure finished", coordinatorId);
    }
}
