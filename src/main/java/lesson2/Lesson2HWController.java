package lesson2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: Создать класс Lesson2HWController с аннотацией @RestController для обработки HTTP запросов
@RestController
public class Lesson2HWController {

    public static void main(String[] args) {
    }
    // TODO: Реализовать метод currentTime():
    @GetMapping("/current-datetime") // http://localhost:8080/current-datetime
    public String currentTime() {
        LocalDateTime time = LocalDateTime.now();
        return "Текущее время: " + time;
    }
    //       - Используя LocalDateTime.now(), получить текущее время
    //       - Вернуть строку с текущим временем в формате "Текущее время: [currentTime]"
    //       - Метод должен быть доступен по GET запросу на "/current-datetime"


    // TODO: Реализовать метод currentSeason():
    @GetMapping("/current-season") // http://localhost:8080/current-season
    public String currentSeason() {
        int month = LocalDate.now().getMonthValue();
        String season = "null";
        switch (month) {
            case 1:
            case 2:
            case 12:
                season = "Зима";
                break;
            case 3:
            case 4:
            case 5:
                season = "Весна";
                break;
            case 6:
            case 7:
            case 8:
                season = "Лето";
                break;
            case 9:
            case 10:
            case 11:
                season = "Осень";
                break;
        }
        return season;
    }
    //       - Получить текущий месяц через LocalDate.now().getMonthValue()
    //       - В зависимости от месяца вернуть сезон:
    //         - Зима (декабрь, январь, февраль)
    //         - Весна (март, апрель, май)
    //         - Лето (июнь, июль, август)
    //         - Осень (сентябрь, октябрь, ноябрь)
    //       - Метод должен быть доступен по GET запросу на "/current-season"


    // TODO: Реализовать метод futureDate():
    @GetMapping("/future-date") // http://localhost:8080/future-date
    public String futureDate(){
        LocalDate date = LocalDate.now();
        Random random = new Random();
        int number = random.nextInt(30) + 1;
        LocalDate futureDate = date.plusDays(number);
        return "Случайная дата в будущем:" + futureDate;
    }
    //       - Получить текущую дату через LocalDate.now()
    //       - Сгенерировать случайное количество дней в пределах 30 (с использованием Random)
    //       - Добавить эти дни к текущей дате для получения будущей даты
    //       - Вернуть строку с будущей датой в формате "Случайная дата в будущем: [futureDate]"
    //       - Метод должен быть доступен по GET запросу на "/future-date"
}