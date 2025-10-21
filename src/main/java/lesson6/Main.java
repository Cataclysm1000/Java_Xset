package lesson6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("src/main/java/lesson6/file_l6.txt"), "UTF-8");
            StringBuilder sb = new StringBuilder();

            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append(" ");
            }
            scanner.close();

            String text = sb.toString().toLowerCase();

            text = text.replaceAll("[^а-я]+", " ");

            String[] words = text.trim().split("\\s+");

            if (words.length == 0 || words[0].equals("")) {
                System.out.println("Файл пустой");
                return;
            }

            // Повторения
            Map<String, Integer> repeat = new HashMap<>();
            for (String x : words) {
                Integer count = repeat.get(x);
                if (count == null) {
                    repeat.put(x, 1);
                } else {
                    repeat.put(x, count + 1);
                }
            }

            // Уникальные слова
            List<String> uniq = new ArrayList<>(repeat.keySet());

            //Сортировка
            Collections.sort(uniq, new Comparator<String>() {
                public int compare(String a, String b) {
                    if (a.length() != b.length()) {
                        return b.length() - a.length();
                    } else {
                        return a.compareTo(b);
                    }
                }
            });

            System.out.println("Отсортированные слова в алфавитном порядке:");
            for (String x : uniq) {
                System.out.println(x);
            }

            System.out.println("Повторения:");
            for (String x : uniq) {
                System.out.println(x + " - " + repeat.get(x));
            }

            int max = 0;
            for (String x : uniq) {
                if (x.length() > max) {
                    max = x.length();
                }
            }

            System.out.println("Самое длинное слово: ");
            for (String x : uniq) {
                if (x.length() == max) {
                    System.out.println(x);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
    }
}
