package anton.kaliadau.course.impl;

import anton.kaliadau.util.DBConnector;
import anton.kaliadau.course.model.Course;
import anton.kaliadau.course.CourseRepository;
import anton.kaliadau.student.model.Student;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class CourseRepositoryImpl implements CourseRepository {

    private final String SQL_SELECT_COURSES = """
            SELECT * FROM course 
            LEFT JOIN course_student ON course.id = course_student.course_id 
            LEFT JOIN student ON student.id = course_student.student_id""";
    private final String SQL_SELECT_COURSE_BY_ID = """
            SELECT * FROM course 
            LEFT JOIN course_student ON course.id = course_student.course_id 
            LEFT JOIN student ON student.id = course_student.student_id 
            WHERE course.id = ?""";
    private final String SQL_INSERT_COURSE = "INSERT INTO course(course_name) VALUES(?)";
    private final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM course WHERE course.id = ?";
    private final String SQL_UPDATE_COURSE_NAME_BY_ID = "UPDATE course SET course_name = ? WHERE course.id = ?";
    private final String SQL_DELETE_ALL_COURSES = "TRUNCATE TABLE course CASCADE";

    @Override
    public Course save(String name) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_INSERT_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return getLazyCourse(resultSet);
            }
        } catch (SQLException e) {
            log.error("Error while creating a course: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Course> findById(Long id) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_SELECT_COURSE_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getCourse(resultSet));
        } catch (SQLException e) {
            log.error("Error while getting a course: {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_DELETE_COURSE_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("Error while deleting a course: {}", e.getMessage());
        }
    }

    @Override
    public void updateNameById(Long id, String newName) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_UPDATE_COURSE_NAME_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys();
        } catch (Exception e) {
            log.error("Error while updating a course: {}", e.getMessage());
        }
    }

    @Override
    public List<Course> findAll() {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_SELECT_COURSES, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            var resultSet = preparedStatement.executeQuery();
            return getCourses(resultSet);
        } catch (SQLException e) {
            log.error("Error while getting all courses: {}", e.getMessage());
        }
        return List.of();
    }

    @Override
    public void deleteAll() {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_DELETE_ALL_COURSES, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting all courses: {}", e.getMessage());
        }
    }

    private Course getLazyCourse(ResultSet resultSet) throws SQLException {
        var courseId = resultSet.getLong("id");
        return Course.builder()
                .id(courseId)
                .name(resultSet.getString("course_name"))
                .students(List.of())
                .build();
    }

    private Course getCourse(ResultSet resultSet) throws SQLException {
        var courseId = resultSet.getLong("id");
        return Course.builder()
                .id(courseId)
                .name(resultSet.getString("course_name"))
                .students(getStudents(resultSet, courseId))
                .build();
    }

    private List<Course> getCourses(ResultSet resultSet) throws SQLException {
        var orders = new ArrayList<Course>();
        while (resultSet.next()) {
            orders.add(getCourse(resultSet));
        }
        return orders;
    }

    private Student getStudent(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .id(resultSet.getLong("student_id"))
                .name(resultSet.getString("student_name"))
                .build();
    }

    private boolean containsStudent(ResultSet resultSet) throws SQLException {
        var metaData = resultSet.getMetaData();
        var columnCount = metaData.getColumnCount();
        for (int i = 1; i < columnCount + 1; i++) {
            var columnName = metaData.getColumnName(i);
            if ("student_name".equals(columnName)) {
                return true;
            }
        }
        return false;
    }

    private List<Student> getStudents(ResultSet resultSet, Long courseId) throws SQLException {
        var students = new ArrayList<Student>();
        do {
            if (containsStudent(resultSet) && resultSet.getLong("student_id") != 0) {
                students.add(getStudent(resultSet));
            }
        } while (resultSet.next() && resultSet.getLong("course_id") == courseId);
        resultSet.previous();
        return students;
    }
}
