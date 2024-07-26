package anton.kaliadau.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ServletUtil {

    public static void sendJson(HttpServletResponse response, Object dto) throws IOException {
        var writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(new Gson().toJson(dto));
        writer.flush();
    }
}
