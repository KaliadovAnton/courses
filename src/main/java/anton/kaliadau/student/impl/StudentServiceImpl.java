package anton.kaliadau.student.impl;

import anton.kaliadau.util.BeanFactory;
import anton.kaliadau.student.model.Student;
import anton.kaliadau.student.StudentRepository;
import anton.kaliadau.student.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl() {
        this.repository = BeanFactory.getStudentRepository();
    }

    @Override
    public Student saveStudent(String studentName) {
        return repository.save(studentName);
    }

    @Override
    public void deleteById(Long studentId) {
        repository.deleteById(studentId);
    }

    @Override
    public void updateNameById(Long studentId, String newName) {
        repository.updateNameById(studentId, newName);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Student updateCoordinator(Long studentId, Long coordinatorId) {
        return repository.updateCoordinator(studentId, coordinatorId).orElseThrow();
    }

    @Override
    public Student findById(Long id) {
        return repository.findByID(id).orElseThrow();
    }
}
