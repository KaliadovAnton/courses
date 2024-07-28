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
import java.util.NoSuchElementException;

@Slf4j
@NoArgsConstructor
@WebServlet("/coordinator/update")
public class CoordinatorUpdateServlet extends HttpServlet {

    private CoordinatorService coordinatorService;

    private final CoordinatorMapper coordinatorMapper = new CoordinatorMapperImpl();

    @Override
    public void init(ServletConfig config) {
        coordinatorService = BeanFactory.getCoordinatorService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Coordinator update procedure started");
        try {
            var coordinatorId = Long.parseLong(req.getParameter("id"));
            var newName = req.getParameter("new_name");
            if (newName == null || newName.isEmpty() || newName.isBlank()) {
                log.info("Incorrect name of coordinator. Procedure finished.");
                resp.sendError(400);
                return;
            }
            coordinatorService.updateNameById(coordinatorId, newName);
            var dispatcher = req.getRequestDispatcher("/WEB-INF/object-updated.jsp");
            dispatcher.forward(req, resp);
            log.info("Coordinator update procedure finished successfully");
        } catch (NumberFormatException e) {
            resp.sendError(400);
            log.info("Coordinator update procedure finished with an exception");
        } catch (NoSuchElementException e) {
            resp.sendError(404);
            log.info("Coordinator update procedure finished with an exception");
        }
    }
}
