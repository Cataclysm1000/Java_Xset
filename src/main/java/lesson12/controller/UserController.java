package lesson12.controller;

import lesson12.model.User;
import lesson12.service.KafkaProducer;
import lesson12.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private KafkaProducer kafkaProducer;

    // Создание пользователя
    @PostMapping("/createUser")
    public String createUser(@RequestBody User user) {
        if (user.getFirstName() == null || user.getLastName() == null)
            return "Ошибка: имя и фамилия не должны быть пустыми";
        if (user.getDepartmentId() <= 0)
            return "Ошибка: некорректный departmentId";
        return kafkaProducer.sendMessage("/createUser", user);
    }

    // Обновление пользователя
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        if (user.getId() == 0)
            return "Ошибка: не указан id пользователя";
        return kafkaProducer.sendMessage("/updateUser", user);
    }

    // Получение всех пользователей
    @GetMapping("/getAllUsers")
    public String getAllUsers() {
        return kafkaProducer.sendMessage("/getAllUsers", "");
    }

    // Получение пользователей по дате рождения
    @GetMapping("/getUserByBirthDate")
    public String getUserByBirthDate(@RequestParam String birthDate) {
        return kafkaProducer.sendMessage("/getUserByBirthDate", birthDate);
    }

    // Удаление пользователей по фамилии
    @DeleteMapping("/deleteUserByLastName")
    public String deleteUserByLastName(@RequestParam String lastName) {
        return kafkaProducer.sendMessage("/deleteUserByLastName", lastName);
    }

    // Очистка таблицы пользователей
    @DeleteMapping("/deleteUsers")
    public String deleteUsers() {
        return kafkaProducer.sendMessage("/deleteUsers", "");
    }

    // Получение всех департаментов
    @GetMapping("/getDepartments")
    public String getDepartments() {
        return kafkaProducer.sendMessage("/getDepartments", "");
    }

    // Получение количества пользователей по департаментам > 5
    @GetMapping("/getAmountUsersByDepartmentAndMore5")
    public String getAmountUsersByDepartmentAndMore5() {
        return kafkaProducer.sendMessage("/getAmountUsersByDepartmentAndMore5", "");
    }

    // Получение логов
    @GetMapping("/log")
    public String getLog() {
        return LoggerService.readLog();
    }
}
