package anton.kaliadau.coursestudent.impl;


import anton.kaliadau.AbstractIntegrationTest;
import anton.kaliadau.course.impl.CourseServiceImpl;
import anton.kaliadau.course.model.Course;
import anton.kaliadau.student.impl.StudentServiceImpl;
import anton.kaliadau.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CourseStudentServiceImplTest extends AbstractIntegrationTest {

    private final CourseStudentServiceImpl courseStudentService = new CourseStudentServiceImpl();
    private final StudentServiceImpl studentService = new StudentServiceImpl();
    private final CourseServiceImpl courseService = new CourseServiceImpl();

    @BeforeEach
    void cleanup() {
        studentService.deleteAll();
        courseService.deleteAll();
    }

    @Test
    void addCourseToStudent() {
        //given: existing course and student
        var student = studentService.saveStudent(studentName1);
        var course1 = courseService.saveCourse(courseName1);
        var course2 = courseService.saveCourse(courseName2);
        var studentId = student.getId();
        var courseId1 = course1.getId();
        var courseId2 = course2.getId();

        //when: add course to student
        courseStudentService.addCourseToStudent(studentId, courseId1);
        courseStudentService.addCourseToStudent(studentId, courseId2);
        var result = studentService.findById(studentId).getCourses().stream().map(Course::getName).toList();

        //then: course is added
        assertEquals(2, result.size());
        assertTrue(result.contains(courseName1));
        assertTrue(result.contains(courseName2));
    }

    @Test
    void addCourseToStudent2() {
        //given: existing course and student
        var student1 = studentService.saveStudent(studentName1);
        var student2 = studentService.saveStudent(studentName2);
        var course1 = courseService.saveCourse(courseName1);
        var studentId1 = student1.getId();
        var studentId2 = student2.getId();
        var courseId = course1.getId();

        //when: add course to student
        courseStudentService.addCourseToStudent(studentId1, courseId);
        courseStudentService.addCourseToStudent(studentId2, courseId);
        var result = courseService.findById(courseId).getStudents().stream().map(Student::getName).toList();

        //then: course is added
        assertEquals(2, result.size());
        assertTrue(result.contains(studentName1));
        assertTrue(result.contains(studentName2));
    }

    @Test
    void addCourseToStudent3() {
        //given: existing course and student
        var student1 = studentService.saveStudent(studentName1);
        var student2 = studentService.saveStudent(studentName2);
        var student3 = studentService.saveStudent(studentName3);
        var course1 = courseService.saveCourse(courseName1);
        var course2 = courseService.saveCourse(courseName2);
        var course3 = courseService.saveCourse(courseName3);
        var studentId1 = student1.getId();
        var studentId2 = student2.getId();
        var studentId3 = student3.getId();
        var courseId1 = course1.getId();
        var courseId2 = course2.getId();
        var courseId3 = course3.getId();
        //when: add course to student
        courseStudentService.addCourseToStudent(studentId1, courseId1);
        courseStudentService.addCourseToStudent(studentId3, courseId1);
        courseStudentService.addCourseToStudent(studentId2, courseId3);
        var result = courseService.findAll();
        //then: course have correct students
        assertEquals(3, result.size());
        var firstCourse = getCourse(result, courseId1);
        var secondCourse = getCourse(result, courseId2);
        var thirdCourse = getCourse(result, courseId3);
        validateCourse(firstCourse, List.of(student1, student3));
        validateCourse(secondCourse, List.of());
        validateCourse(thirdCourse, List.of(student2));
    }

    private void validateCourse(Course course, List<Student> students) {
        var courseStudents = course.getStudents();
        assertEquals(students.size(), courseStudents.size());
        for (Student student : students) {
            assertTrue(courseStudents.stream()
                    .map(Student::getId)
                    .toList()
                    .contains(student.getId()));
            assertTrue(courseStudents.stream()
                    .map(Student::getName)
                    .toList()
                    .contains(student.getName()));
        }
    }

    private Course getCourse(List<Course> courses, Long id) {
        return courses.stream()
                .filter(course -> Objects.equals(course.getId(), id))
                .findFirst()
                .get();
    }
}