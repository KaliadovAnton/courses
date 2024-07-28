package anton.kaliadau.coordinator.impl;

import anton.kaliadau.util.DBConnector;
import anton.kaliadau.coordinator.model.Coordinator;
import anton.kaliadau.coordinator.CoordinatorRepository;
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
public class CoordinatorRepositoryImpl implements CoordinatorRepository {

    private final String SQL_SELECT_COORDINATORS = "SELECT * FROM coordinator LEFT JOIN student ON student.coordinator_id = coordinator.id";
    private final String SQL_SELECT_COORDINATOR = "SELECT * FROM coordinator LEFT JOIN student ON student.coordinator_id = coordinator.id WHERE coordinator.id = ?";
    private final String SQL_DELETE_COORDINATOR_BY_ID = "DELETE FROM coordinator WHERE coordinator.id = ?";
    private final String SQL_INSERT_COORDINATOR = "INSERT INTO coordinator(coordinator_name) VALUES(?)";
    private final String SQL_UPDATE_COORDINATOR_NAME_BY_ID = "UPDATE coordinator SET coordinator_name = ? WHERE coordinator.id = ?";
    private final String SQL_DELETE_ALL_COORDINATORS = "TRUNCATE TABLE coordinator CASCADE";

    @Override
    public Coordinator save(String coordinatorName) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_INSERT_COORDINATOR, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, coordinatorName);
            preparedStatement.executeUpdate();
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return getLazyCoordinator(resultSet);
            }
        } catch (SQLException e) {
            log.error("Error while creating a coordinator: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Long coordinatorId) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_DELETE_COORDINATOR_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, coordinatorId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("Error while deleting a coordinator: {}", e.getMessage());
        }
    }

    @Override
    public void updateNameById(Long coordinatorId, String newName) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_UPDATE_COORDINATOR_NAME_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, coordinatorId);
            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys();
        } catch (Exception e) {
            log.error("Error while updating a coordinator: {}", e.getMessage());
        }
    }

    @Override
    public List<Coordinator> findAll() {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_SELECT_COORDINATORS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            var resultSet = preparedStatement.executeQuery();
            return getCoordinators(resultSet);
        } catch (SQLException e) {
            log.error("Error while getting all students: {}", e.getMessage());
        }
        return List.of();
    }

    @Override
    public void deleteAll() {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_DELETE_ALL_COORDINATORS)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting all students: {}", e.getMessage());
        }
    }

    @Override
    public Optional<Coordinator> findById(Long id) {
        try (var connection = DBConnector.getConnection();
             var preparedStatement = Objects.requireNonNull(connection)
                     .prepareStatement(SQL_SELECT_COORDINATOR, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCoordinatorsWithStudents(resultSet));
            }
        } catch (SQLException e) {
            log.error("Error while getting all students: {}", e.getMessage());
        }
        return Optional.empty();
    }

    private List<Coordinator> getCoordinators(ResultSet resultSet) throws SQLException {
        var coordinators = new ArrayList<Coordinator>();
        while (resultSet.next()) {
            coordinators.add(getCoordinatorsWithStudents(resultSet));
        }
        return coordinators;
    }

    private Coordinator getLazyCoordinator(ResultSet resultSet) throws SQLException {
        return Coordinator.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("coordinator_name"))
                .students(List.of())
                .build();
    }

    private Coordinator getCoordinatorsWithStudents(ResultSet resultSet) throws SQLException {
        var coordinatorId = resultSet.getLong(1);
        return Coordinator.builder()
                .id(coordinatorId)
                .name(resultSet.getString("coordinator_name"))
                .students(getStudents(resultSet, coordinatorId))
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

    private List<Student> getStudents(ResultSet resultSet, Long coordinatorId) throws SQLException {
        var students = new ArrayList<Student>();
        do {
            if (containsStudent(resultSet) && resultSet.getLong(3) != 0) {
                students.add(getStudent(resultSet));
            }
        } while (resultSet.next() && resultSet.getLong(1) == coordinatorId);
        resultSet.previous();
        return students;
    }

    private Student getStudent(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .id(resultSet.getLong(3))
                .name(resultSet.getString("student_name"))
                .build();
    }
}
