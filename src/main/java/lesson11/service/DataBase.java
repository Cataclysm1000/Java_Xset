package lesson11.service;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataBase {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initData(){
        jdbcTemplate.execute("DROP TABLE IF EXISTS enrollments, courses, students CASCADE");

        jdbcTemplate.execute("""
                CREATE TABLE students(
                student_id SERIAL PRIMARY KEY,
                first_name VARCHAR(50) NOT NULL,
                last_name VARCHAR(50) NOT NULL,
                birth_date DATE NOT NULL,
                group_name VARCHAR(20) NOT NULL
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE courses(
                course_id SERIAL PRIMARY KEY,
                course_name VARCHAR(100) NOT NULL,
                lecturer VARCHAR(100) NOT NULL
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE enrollments(
                enrollment_id SERIAL PRIMARY KEY,
                student_id INT REFERENCES students(student_id) ON DELETE CASCADE,
                course_id INT REFERENCES courses(course_id) ON DELETE CASCADE,
                grade INT CHECK (grade BETWEEN 0 AND 10)
                );
                """);

        jdbcTemplate.update("INSERT INTO students (first_name, last_name, birth_date, group_name) VALUES ('Иван', 'Петров', '2000-01-01', 'CS-21')");
        jdbcTemplate.update("INSERT INTO students (first_name, last_name, birth_date, group_name) VALUES ('Анна', 'Сидорова', '2001-02-02', 'CS-21')");
        jdbcTemplate.update("INSERT INTO students (first_name, last_name, birth_date, group_name) VALUES ('Дмитрий', 'Иванов', '2000-03-03', 'CS-22')");
    }
}
