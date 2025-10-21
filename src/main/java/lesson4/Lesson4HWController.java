package lesson4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

@RestController
public class Lesson4HWController {

    public static void main(String[] args) {
    }

    // TODO: Реализовать метод для вычисления дня недели по переданной дате
    //       - Метод должен принимать строку с датой в формате "yyyy-MM-dd".
    //       - Преобразовать строку в LocalDate и вернуть день недели (например, Понедельник, Вторник и т.д.)
    //       - Метод должен быть доступен по GET запросу на "/day-of-week"
    @GetMapping("/day-of-week")  // http://localhost:8080/day-of-week?date=2025-10-14
    public String dayWeek(@RequestParam String date){
        LocalDate localDate = LocalDate.parse(date);
        return localDate.getDayOfWeek().toString();
        }

    // TODO: Реализовать метод для генерации случайного пароля переданной длины
    //       - Метод должен принимать параметр "length" (длина пароля).
    //       - Сгенерировать случайный пароль из букв (верхний и нижний регистр), цифр и специальных символов.
    //       - Вернуть строку с результатом в формате "Случайный пароль: [randomPassword]"
    //       - Метод должен быть доступен по GET запросу на "/generate-password"

    @GetMapping("/generate-password") // http://localhost:8080/generate-password?length=10
    public String password(@RequestParam("length") int length){
        String ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_!";
        StringBuilder randomPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ch.length());
            randomPassword.append(ch.charAt(index));
        }
        return "Случайный пароль: " + randomPassword;
    }

    // TODO: Реализовать метод для вычисления факториала от числа
    //       - Метод должен принимать параметр "number" (целое число).
    //       - Рассчитать факториал числа по формуле: factorial(n) = n * (n-1) * (n-2) * ... * 1.
    //       - Вернуть строку с результатом в формате "Факториал числа [number]: [factorial]"
    //       - Метод должен быть доступен по GET запросу на "/factorial"

    @GetMapping("/factorial") // http://localhost:8080/factorial?number=10
    public String number(@RequestParam("number") int number){
        long factorial = 1;
        for (int i = 1; i <= number ; i++) {
            factorial *= i;
        }
        return  "Факториал числа " + number + ": " + factorial;
    }


    // TODO: Реализовать метод для возведения числа в степень
    //       - Метод должен принимать два параметра: число и степень.
    //       - Возвести число в указанную степень.
    //       - Вернуть строку с результатом в формате "[number] в степени [power]: [result]"
    //       - Метод должен быть доступен по GET запросу на "/power."
    @GetMapping("/power")  // http://localhost:8080/power?number=2&power=4
    public String pow(@RequestParam("number") int number, @RequestParam("power") int power){
        double result = Math.pow(number, power);
        return number + " в степени " + power + ": " + result;
    }

    // TODO: Реализовать метод для генерации случайной даты между двумя переданными
    //       - Метод должен принимать два параметра: startDate и endDate (в формате "yyyy-MM-dd").
    //       - Сгенерировать случайную дату в диапазоне между этими двумя датами.
    //       - Вернуть строку с результатом в формате "Случайная дата: [randomDate]"
    //       - Метод должен быть доступен по GET запросу на "/random-date"\

    @GetMapping("/random-date") // http://localhost:8080/random-date?startDate=2025-10-01&endDate=2025-10-14
    public String date(@RequestParam String startDate, @RequestParam String endDate){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        long startDays = start.toEpochDay();
        long endDays = end.toEpochDay();

        Random random = new Random();
        long randomDays = startDays + random.nextInt((int) (endDays - startDays + 1));

        LocalDate randomDate = LocalDate.ofEpochDay(randomDays);

        return "Случайная дата: " + randomDate;
    }

    // TODO: Реализовать метод для сортировки массива с учетом параметра isAsc
    //       - Метод должен принимать массив чисел и параметр "isAsc" (логическое значение).
    //       - Если isAsc = true, отсортировать массив по возрастанию, если false — по убыванию.
    //       - Вернуть строку с отсортированным массивом в формате "Отсортированный массив: [sortedArray]"
    //       - Метод должен быть доступен по GET запросу на "/sort-array"
    @GetMapping("/sort-array") // http://localhost:8080/sort-array?numbers=5&numbers=2&numbers=8&numbers=1&isAsc=true
    public String sortArray(@RequestParam int[] numbers, @RequestParam boolean isAsc){
        Arrays.sort(numbers);
        if (!isAsc){
            for (int i = 0; i < numbers.length / 2; i++) {
                int temp = numbers[i];
                numbers[i] = numbers[numbers.length - 1 - i];
                numbers[numbers.length - 1 - i] = temp;
            }
        }
        return "Отсортированный массив: " + Arrays.toString(numbers);
    }

    // TODO: Реализовать метод для разделения строки по позиции и отправки части строки
    //       - Метод должен принимать строку (str), позицию (position) и флаг (isFirst).
    //       - Если isFirst = true, вернуть первую часть строки до указанной позиции, иначе — вторую часть после позиции.
    //       - Вернуть строку в формате "Часть строки: [substring]"
    //       - Метод должен быть доступен по GET запросу на "/substring"
    @GetMapping("/substring") // http://localhost:8080/substring?str=abcdef&position=3&isFirst=false

    public String getSubstring(@RequestParam String str, @RequestParam int position, @RequestParam boolean isFirst){
        String substring;
        if (isFirst) {
            substring = str.substring(0, position);
        } else {
            substring = str.substring(position);
        }
        return "Часть строки: " + substring;
    }
}
