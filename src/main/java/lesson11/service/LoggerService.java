package lesson11.service;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class LoggerService {
    private static final String LOGS = "logs.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Запись лога
    public static void writeLog(String infoMessage, String sendObject) {
        try (FileWriter fw = new FileWriter(LOGS, true)) {
            fw.write(LocalDateTime.now() + " | " + infoMessage + " | " + sendObject + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Получение логов за указанный период
    @GetMapping("/logs")
    public String readLogs(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(LOGS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String timestamp = line.split("\\|")[0].trim();
                LocalDateTime time = LocalDateTime.parse(timestamp, FORMATTER);
                if (!time.isBefore(from) && !time.isAfter(to)) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            return "Ошибка при чтении логов: " + e.getMessage();
        }
        return sb.toString();
    }
}