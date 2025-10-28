package lesson8;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;

@RestController
public class LoggerService{
    private static final String LOGS = "logs.txt";

    public static void writeLog(String infoMessage, String sendObject) {
        try(FileWriter fw = new FileWriter(LOGS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)){
            out.println(LocalDateTime.now() + ": " + infoMessage + ": " + sendObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getLogs")
    public String readLog() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(LOGS))){
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Ошибка в логе: " + e.getMessage();
        }
        return sb.toString();
    }
}

//Реализовать отдельный класс LogerService, в котором должны быть два метода:

// Требования для writeLog(Stirng infoMessage, String sendObject)
// 1. infoMessage - информативное сообщение (хардкод)
// 2. sendObject - отправляемый ответ на запросы
// 3. Метод writeLog должен писать в текстовый файл сообщение вида -  "LocalDateTime.now(): infoMessage: sendObject"
// 4. Лог не должен удаляться при перезапуске программы

// Требования для readLog()
// 1. Данный метод вызывается по Get-request /getLogs
// 2. Метод должен возвращать все логи, записанные в текстовом файле