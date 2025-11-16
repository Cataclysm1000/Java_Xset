package lesson12.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lesson12.model.KafkaMessage;
import lesson12.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class KafkaConsumer {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @KafkaListener(topics = "finalProject", groupId = "group_id")
    public void consume(String messageJson) {
        try {
            KafkaMessage kafkaMessage = objectMapper.readValue(messageJson, KafkaMessage.class);
            String url = kafkaMessage.getUrlRequest();

            System.out.println("\nСообщение из Kafka:");
            System.out.println("URL: " + url);
            System.out.println("Message: " + kafkaMessage.getMessage());

            switch (url) {
                // Создание пользователя
                case "/createUser" -> {
                    User user = objectMapper.convertValue(kafkaMessage.getMessage(), User.class);
                    jdbcTemplate.update(
                            "INSERT INTO users (firstname, lastname, birthday, departmentid) VALUES (?, ?, ?, ?)",
                            user.getFirstName(),
                            user.getLastName(),
                            Date.valueOf(user.getBirthDay()),
                            user.getDepartmentId()
                    );
                    LoggerService.writeLog(url, "Создан новый пользователь: " + objectMapper.writeValueAsString(user));
                }

                // Обновление пользователя
                case "/updateUser" -> {
                    User user = objectMapper.convertValue(kafkaMessage.getMessage(), User.class);
                    var oldUser = jdbcTemplate.queryForMap("SELECT * FROM users WHERE id = ?", user.getId());
                    LoggerService.writeLog(url, "Данные пользователя до обновления: " + oldUser);
                    jdbcTemplate.update(
                            "UPDATE users SET firstname=?, lastname=?, birthday=?, departmentid=? WHERE id=?",
                            user.getFirstName(),
                            user.getLastName(),
                            Date.valueOf(user.getBirthDay()),
                            user.getDepartmentId(),
                            user.getId()
                    );
                    LoggerService.writeLog(url, "Данные пользователя после обновления: " + objectMapper.writeValueAsString(user));
                }

                // Получение всех пользователей
                case "/getAllUsers" -> {
                    List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");
                    System.out.println(users);
                    LoggerService.writeLog(url, users.toString());
                }

                // Получение пользователей по дате рождения
                case "/getUserByBirthDate" -> {
                    String birthDate = kafkaMessage.getMessage().toString();
                    List<Map<String, Object>> result = jdbcTemplate.queryForList(
                            "SELECT * FROM users WHERE birthday = ?",
                            Date.valueOf(birthDate)
                    );
                    System.out.println(result);
                    LoggerService.writeLog(url, result.toString());
                }

                // Удаление пользователей по фамилии
                case "/deleteUserByLastName" -> {
                    String lastName = kafkaMessage.getMessage().toString();
                    jdbcTemplate.update("DELETE FROM users WHERE lastname = ?", lastName);
                    LoggerService.writeLog(url, "Пользователь с фамилией '" + lastName + "' удалён");
                }

                // Очистка таблицы пользователей
                case "/deleteUsers" -> {
                    jdbcTemplate.update("TRUNCATE TABLE users RESTART IDENTITY CASCADE");
                    LoggerService.writeLog(url, "Все пользователи удалены");
                }

                // Получение всех департаментов
                case "/getDepartments" -> {
                    List<Map<String, Object>> deps = jdbcTemplate.queryForList("SELECT * FROM department");
                    System.out.println(deps);
                    LoggerService.writeLog(url, deps.toString());
                }

                // Получение количества пользователей по департаментам > 5
                case "/getAmountUsersByDepartmentAndMore5" -> {
                    List<Map<String, Object>> stats = jdbcTemplate.queryForList("""
                            SELECT d.name_department, COUNT(u.id) AS user_count
                            FROM users u
                            JOIN department d ON u.departmentid = d.id
                            GROUP BY d.name_department
                            HAVING COUNT(u.id) > 5
                            """);
                    System.out.println(stats);
                    LoggerService.writeLog(url, stats.toString());
                }

                default -> {
                    System.out.println("Неизвестный URL: " + url);
                    LoggerService.writeLog(url, "Неизвестный запрос");
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            LoggerService.writeLog("ERROR", e.getMessage());
        }
    }
}
