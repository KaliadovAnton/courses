package anton.kaliadau.coordinator.servlet;

import anton.kaliadau.coordinator.CoordinatorService;
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
@WebServlet("/coordinator/remove")
public class CoordinatorRemovalServlet extends HttpServlet {

    private CoordinatorService coordinatorService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        coordinatorService = BeanFactory.getCoordinatorService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Coordinator deletion procedure started");
        try {
            var coordinatorId = Long.parseLong(req.getParameter("id"));
            coordinatorService.deleteById(coordinatorId);
            resp.setStatus(200);
            var dispatcher = req.getRequestDispatcher("/WEB-INF/object-deleted.jsp");
            dispatcher.forward(req, resp);
            log.info("Coordinator deletion procedure finished successfully");
        } catch (NumberFormatException e) {
            resp.sendError(400);
            log.info("Coordinator deletion procedure finished with an exception");
        } catch (NoSuchElementException e) {
            resp.sendError(404);
            log.info("Coordinator deletion procedure finished with an exception");
        }
    }
}
