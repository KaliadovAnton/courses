package anton.kaliadau.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.SQLException;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    private static final String SQL = """
                CREATE TABLE IF NOT EXISTS course (
                id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
                name TEXT NOT NULL,
                PRIMARY KEY(id));
                
                CREATE TABLE IF NOT EXISTS coordinator (
                id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
                name TEXT NOT NULL UNIQUE,
                PRIMARY KEY(id));
                
                CREATE TABLE IF NOT EXISTS student (
                id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
                name TEXT NOT NULL,
                coordinator_id BIGINT,
                PRIMARY KEY(id),
                CONSTRAINT fk_coordinator
                   FOREIGN KEY(coordinator_id)
                     REFERENCES coordinator(id));
               
               CREATE TABLE IF NOT EXISTS course_student (
               course_id BIGINT NOT NULL,
               student_id BIGINT NOT NULL,
               CONSTRAINT fk_course
                  FOREIGN KEY(course_id)
                    REFERENCES course(id),
               CONSTRAINT fk_student
                   FOREIGN KEY(student_id)
                     REFERENCES student(id));
            """;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var connection = DBConnector.getConnection();
        try {
            var statement = connection.createStatement();
            statement.execute(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
