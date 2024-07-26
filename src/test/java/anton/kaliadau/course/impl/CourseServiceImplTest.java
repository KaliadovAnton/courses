package anton.kaliadau.course.impl;

import anton.kaliadau.AbstractIntegrationTest;
import anton.kaliadau.course.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest extends AbstractIntegrationTest {

    private final CourseServiceImpl courseService = new CourseServiceImpl();

    @BeforeEach
    void cleanup() {
        courseService.deleteAll();
    }

    @Test
    void findAll() {
        //given: several students exists
        var expected = 2;
        courseService.saveCourse(courseName1);
        courseService.saveCourse(courseName2);
        //when: trying to get list of students
        var result = courseService.findAll();
        //then: get every known student
        assertEquals(expected, result.size());
        assertEquals(courseName1, result.get(0).getName());
        assertEquals(courseName2, result.get(1).getName());
    }

    @Test
    void updateNameById() {
        //given: in repository passed course with changed name
        courseService.saveCourse(courseName1);
        //when: updating student
        courseService.updateNameById(id, newName);
        var result = courseService.findById(id);
        //then: result is expected
        assertEquals(newName, result.getName());
    }

    @Test
    void saveCourse() {
        //given: course name to save
        //when: saving course
        var result = courseService.saveCourse(courseName1);
        //then: course is saved
        assertEquals(courseName1, result.getName());
    }

    @Test
    void deleteById() {
        //given: course to delete
        var givenCourse = courseService.saveCourse(courseName1);
        //when: deleting course
        courseService.deleteById(givenCourse.getId());
        //then: course is deleted
        assertFalse(courseService.findAll().stream().anyMatch(course -> course.getName().equals(courseName1)));
    }

    @Test
    void getById() {
        //given: existing courses
        var course = courseService.saveCourse(courseName1);
        //when: find by id
        var result = courseService.findById(course.getId());
        //then:
        assertEquals(result, course);
    }
}