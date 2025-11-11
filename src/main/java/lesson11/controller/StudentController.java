package lesson11.controller;

import lesson11.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Все студенты отсортированные по фамилии http://localhost:8080/students
    @GetMapping
    public List<Map<String, Object>> getAllStudents() {
        List<Map<String, Object>> students = jdbcTemplate.queryForList("SELECT * FROM students ORDER BY last_name");
        LoggerService.writeLog("getAllStudents", students.toString());
        return students;
    }

    //Получить студента по id http://localhost:8080/students/1
    @GetMapping("/{id}")
    public Map<String, Object> getStudentById(@PathVariable int id) {
        Map<String, Object> student = jdbcTemplate.queryForMap("SELECT * FROM students WHERE student_id = ?", id);
        LoggerService.writeLog("getStudentById=" + id, student.toString());
        return student;
    }

    //Получить студентов по названию группы http://localhost:8080/students/group/CS-21
    @GetMapping("/group/{group}")
    public List<Map<String, Object>> getStudentsByGroup(@PathVariable String group) {
        List<Map<String, Object>> students = jdbcTemplate.queryForList("SELECT * FROM students WHERE group_name = ?", group);
        LoggerService.writeLog("getStudentsByGroup=" + group, students.toString());
        return students;
    }

    //Обновить имя и группу по id  http://localhost:8080/students/1?firstName=Павел&groupName=CS-25
    @PutMapping("/{id}")
    public void updateStudent(@PathVariable int id,
                              @RequestParam String firstName,
                              @RequestParam String groupName) {
        jdbcTemplate.update("UPDATE students SET first_name = ?, group_name = ? WHERE student_id = ?", firstName, groupName, id);
        LoggerService.writeLog("updateStudent id=" + id, "new name=" + firstName + ", new group=" + groupName);
    }

    //Добавить нового студента
    @PostMapping
    public void addStudent(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String birthDate,
                           @RequestParam String groupName) {
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);
        jdbcTemplate.update("INSERT INTO students (first_name, last_name, birth_date, group_name) VALUES (?, ?, ?, ?)",
                firstName, lastName, sqlDate, groupName);
        LoggerService.writeLog("addStudent", firstName + " " + lastName + " (" + groupName + ")");
    }

    //Удалить студента http://localhost:8080/students/3
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        jdbcTemplate.update("DELETE FROM students WHERE student_id = ?", id);
        LoggerService.writeLog("deleteStudent id=" + id, "deleted");
    }
}
