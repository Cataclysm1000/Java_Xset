package lesson12.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerService {
    private static final String LOGS = "logs.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static void writeLog(String urlRequest, String message) {
        try (FileWriter fw = new FileWriter(LOGS, true)) {
            fw.write(LocalDateTime.now().format(FORMATTER) + " | " + urlRequest + " | " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readLog() {
        try {
            Path path = Path.of(LOGS);
            return Files.exists(path) ? Files.readString(path) : "Лог пуст.";
        } catch (IOException e) {
            return "Ошибка чтения лога: " + e.getMessage();
        }
    }
}
