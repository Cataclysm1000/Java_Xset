package lesson12.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataBase {

    private final JdbcTemplate jdbcTemplate;

    public DataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createTables();
    }

    private void createTables() {
        // Создание department и добавление записей
        jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS department (
                        id SERIAL PRIMARY KEY,
                        name_department TEXT NOT NULL UNIQUE
                    );

                    INSERT INTO department (name_department)
                    SELECT name_department FROM (VALUES ('IT'), ('HR'), ('Sales')) AS v(name_department)
                    WHERE NOT EXISTS (SELECT 1 FROM department);
                """);

        // Создание users
        jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        id SERIAL PRIMARY KEY,
                        firstname TEXT NOT NULL,
                        lastname TEXT NOT NULL,
                        birthday DATE,
                        departmentid INT REFERENCES department(id),
                        UNIQUE (firstname, lastname)
                    );
                """);
    }

    // Создание пользователя
    public void createUser(String firstName, String lastName, String birthDay, int departmentId) {
        String sql = "INSERT INTO users (firstname, lastname, birthday, departmentid) VALUES (?, ?, ?, ?)";
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDay);
        jdbcTemplate.update(sql, firstName, lastName, sqlDate, departmentId);
    }

    // Обновление пользователя
    public void updateUser(int id, String firstName, String lastName, String birthDay, int departmentId) {
        String sql = "UPDATE users SET firstname=?, lastname=?, birthday=?, departmentid=? WHERE id=?";
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDay);
        jdbcTemplate.update(sql, firstName, lastName, sqlDate, departmentId, id);
    }

    // Получение всех пользователей
    public List<Map<String, Object>> getAllUsers() {
        return jdbcTemplate.queryForList("SELECT * FROM users");
    }

    // Получение пользователей по дате рождения
    public List<Map<String, Object>> getUserByBirthDate(String birthDate) {
        return jdbcTemplate.queryForList("SELECT * FROM users WHERE birthday = ?", birthDate);
    }

    // Удаление пользователей по фамилии
    public void deleteUserByLastName(String lastName) {
        jdbcTemplate.update("DELETE FROM users WHERE lastname = ?", lastName);
    }

    // Очистка таблицы пользователей
    public void deleteUsers() {
        jdbcTemplate.update("TRUNCATE TABLE users RESTART IDENTITY CASCADE");
    }

    // Получение всех департаментов
    public List<Map<String, Object>> getDepartments() {
        return jdbcTemplate.queryForList("SELECT * FROM department");
    }

    // Получение количества пользователей по департаментам > 5
    public List<Map<String, Object>> getAmountUsersByDepartmentAndMore5() {
        String sql = """
                    SELECT d.name_department, COUNT(u.id) AS count_users
                    FROM department d
                    JOIN users u ON d.id = u.departmentid
                    GROUP BY d.name_department
                    HAVING COUNT(u.id) > 5
                """;
        return jdbcTemplate.queryForList(sql);
    }
}
