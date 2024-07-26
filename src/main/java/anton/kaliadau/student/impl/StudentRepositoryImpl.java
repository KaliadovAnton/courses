package anton.kaliadau.student.impl;

import anton.kaliadau.util.DBConnector;
import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.course.model.Course;
import anton.kaliadau.student.model.Student;
import anton.kaliadau.student.StudentRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    private static final String SQL_SELECT_STUDENTS = """
            SELECT * FROM student 
            LEFT JOIN coordinator ON student.coordinator_id = coordinator.id 
            LEFT JOIN course_student ON student.id = course_student.student_id 
            LEFT JOIN course ON course.id = course_student.course_id;
            """;
    private static final String SQL_SELECT_STUDENT = """
            SELECT * FROM student 
            LEFT JOIN coordinator ON student.coordinator_id = coordinator.id 
            LEFT JOIN course_student ON student.id = course_student.student_id 
            LEFT JOIN course ON course.id = course_student.course_id
            WHERE student.id = ?""";
    public static final String SQL_INSERT_STUDENT = "INSERT INTO student(student_name) VALUES(?)";
    public static final String SQL_DELETE_STUDENT_BY_ID = "DELETE FROM student WHERE student.id = ?";
    public static final String SQL_UPDATE_STUDENT_NAME_BY_ID = "UPDATE student SET student_name = ? WHERE student.id = ?";
    public static final String SQL_UPDATE_STUDENT_COORDINATOR_BY_ID = "UPDATE student SET coordinator_id = ? WHERE student.id = ?";
    public static final String SQL_DELETE_ALL_STUDENTS = "TRUNCATE TABLE student CASCADE";
    public static final String SQL_ADD_COURSE_TO_STUDENT = "INSERT INTO course_student(course_id, student_id) VALUES(?, ?)";


    @Override
    public Student save(String studentName) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, studentName);
            preparedStatement.executeUpdate();
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return getLazyStudent(resultSet);
            }
        } catch (SQLException e) {
            log.error("Error while creating a student: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Long studentId) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_DELETE_STUDENT_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, studentId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("Error while deleting a student: {}", e.getMessage());
        }
    }

    @Override
    public void updateNameById(Long studentId, String newName) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_UPDATE_STUDENT_NAME_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, studentId);
            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys();
        } catch (Exception e) {
            log.error("Error while updating a student: {}", e.getMessage());
        }
    }

    @Override
    public List<Student> findAll() {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_SELECT_STUDENTS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            var resultSet = preparedStatement.executeQuery();
            return getStudents(resultSet);
        } catch (SQLException e) {
            log.error("Error while getting all students: {}", e.getMessage());
        }
        return List.of();
    }

    @Override
    public void deleteAll() {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_DELETE_ALL_STUDENTS)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting all students: {}", e.getMessage());
        }
    }

    @Override
    public Optional<Student> updateCoordinator(Long studentId, Long coordinatorId) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_UPDATE_STUDENT_COORDINATOR_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, coordinatorId);
            preparedStatement.setLong(2, studentId);
            preparedStatement.executeUpdate();
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return Optional.of(getLazyStudent(resultSet));
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error while updating coordinator for a student: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void addCourseToStudent(Long studentId, Long courseId) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_ADD_COURSE_TO_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, courseId);
            preparedStatement.setLong(2, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while adding course to a student: {}", e.getMessage());
        }
    }

    @Override
    public Optional<Student> findByID(Long id) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_SELECT_STUDENT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getStudent(resultSet));
            }
        } catch (SQLException e) {
            log.error("Error while getting all students: {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Student getStudentWithCoordinator(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("student_name"))
                .coordinator(Coordinator.builder()
                        .id(resultSet.getLong("coordinator_id"))
                        .name(resultSet.getString("coordinator_name"))
                        .build())
                .build();
    }

    private Student getLazyStudent(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("student_name"))
                .coordinator(getCoordinator(resultSet))
                .build();
    }

    private Student getStudent(ResultSet resultSet) throws SQLException {
        var studentId = resultSet.getLong("id");
        return Student.builder()
                .id(studentId)
                .name(resultSet.getString("student_name"))
                .coordinator(getCoordinator(resultSet))
                .courses(getCourses(resultSet, studentId))
                .build();
    }

    private Coordinator getCoordinator(ResultSet resultSet) throws SQLException {
        if (containsNeededEntity(resultSet, "coordinator_name")) {
            return Coordinator.builder()
                    .id(resultSet.getLong("coordinator_id"))
                    .name(resultSet.getString("coordinator_name"))
                    .build();
        }
        return Coordinator.builder().build();
    }

    private List<Student> getStudents(ResultSet resultSet) throws SQLException {
        var students = new ArrayList<Student>();
        while (resultSet.next()) {
            students.add(getStudentWithCoordinator(resultSet));
        }
        return students;
    }

    private boolean containsNeededEntity(ResultSet resultSet, String columnName) throws SQLException {
        var metaData = resultSet.getMetaData();
        var columnCount = metaData.getColumnCount();
        for (int i = 1; i < columnCount + 1; i++) {
            var existingColumnName = metaData.getColumnName(i);
            if (columnName.equals(existingColumnName)) {
                return true;
            }
        }
        return false;
    }

    private List<Course> getCourses(ResultSet resultSet, Long studentId) throws SQLException {
        var courses = new ArrayList<Course>();
        do {
            if (containsNeededEntity(resultSet, "course_name") && resultSet.getLong("course_id") != 0) {
                courses.add(getCourse(resultSet));
            }
        } while (resultSet.next() && resultSet.getLong(1) == studentId);
        resultSet.previous();
        return courses;
    }

    private Course getCourse(ResultSet resultSet) throws SQLException {
        return Course.builder()
                .id(resultSet.getLong("course_id"))
                .name(resultSet.getString("course_name"))
                .build();
    }
}
