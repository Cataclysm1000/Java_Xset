package lesson7;

import java.time.LocalDate;
import java.util.*;

public class homeWork {
    public static void main(String[] args) {
        String jsonDate = "{\"startDate\": \"2024-05-01\", \"endDate\": \"2024-05-10\"}";
        System.out.println(randomDate(jsonDate));

        String numbers = "{\"numbers\": [5, 3, 1, 4, 2]}";
        System.out.println(sortedNumbers(numbers, true));
        System.out.println(sortedNumbers(numbers, false));

        String jsonText = "{\"text\": \"hello world\"}";
        System.out.println(charJson(jsonText));

        String jsonSum = "{\"numbers\": [1, 2, 3, 4, 5]}";
        System.out.println(sum(jsonSum));

        String jsonSum_2 = "{\"numbers\": [5, 10, 15, 20], \"conditions\": [true, false, true, false]}";
        System.out.println(sumif(jsonSum_2));
    }


    // TODO: Вернуть случайную дату между двумя переданными (передавать JSON)
    // 1. Принять JSON с полями "startDate" и "endDate" (формат YYYY-MM-DD)
    // 2. Сгенерировать случайную дату в этом диапазоне
    // 3. Вернуть JSON с ключом "randomDate"

    public static String randomDate (String jsonDate){
        String startDate = jsonDate.substring(jsonDate.indexOf("startDate") + 13, jsonDate.indexOf("\",", jsonDate.indexOf("startDate")));
        String endDate = jsonDate.substring(jsonDate.indexOf("endDate") + 11, jsonDate.indexOf("\"", jsonDate.indexOf("endDate") + 11));

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        long startDays = start.toEpochDay();
        long endDays = end.toEpochDay();

        Random random = new Random();
        long randomDays = startDays + random.nextInt((int) (endDays - startDays + 1));

        LocalDate randomDate = LocalDate.ofEpochDay(randomDays);

        return "{\"randomDate\":\"" + randomDate + "\"}";
    }
    //обработка входящего тела запроса через Substring -> отдаем хардкод с нужными данными

    // TODO: Вернуть отсортированный массив, учитывая параметр isAsc (передавать JSON)
    // 1. Принять JSON с массивом "numbers" в Body и булевым флагом "isAsc" в параметре
    // 2. Отсортировать массив по возрастанию/убыванию в зависимости от isAsc
    // 3. Вернуть JSON с ключом "sortedNumbers"

    public static String sortedNumbers (String numbers, boolean isAsc){

        //json в массив:
        String str = numbers.substring(numbers.indexOf("[") + 1, numbers.indexOf("]"));
        String[] arr = str.split(",");
        int[] nums = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            nums[i] = Integer.parseInt(arr[i].trim());
        }

        //Сортировка
        Arrays.sort(nums);
        if (!isAsc){
            for (int i = 0; i < nums.length / 2; i++) {
                int temp = nums[i];
                nums[i] = nums[nums.length - 1 - i];
                nums[nums.length - 1 - i] = temp;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"sortedNumbers\": [");
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]);
            if (i < nums.length - 1) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }


    // TODO: Вернуть частоту символов в переданной строке (отсортировано по убыванию)
    // 1. Принять JSON с ключом "text"
    // 2. Подсчитать количество вхождений каждого символа (игнорировать пробелы)
    // 3. Вернуть JSON с отсортированным списком символов и их частот

    public static String charJson (String jsonText) {
        String text = jsonText.substring(jsonText.indexOf("text") + 8, jsonText.lastIndexOf("\""));

        //Подсчёт символов:
        Map<Character, Integer> sort = new HashMap<>();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                continue;
            }
            Integer count = sort.get(c);
            if (count == null) {
                count = 0;
                sort.put(c, count + 1);
            }
        }

        //Сортировка:
        List<Character> chars = new ArrayList<>(sort.keySet());
        Collections.sort(chars, new Comparator<Character>() {
            public int compare(Character a, Character b) {
                return sort.get(b) - sort.get(a);
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append("{\"sort\": [");
        for (int i = 0; i < chars.size(); i++) {
            char c = chars.get(i);
            sb.append("{\"").append(c).append("\": ").append(sort.get(c)).append("}");
            if (i < chars.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }


    // TODO: Реализовать метод sum
    // 1. Принять JSON с массивом "numbers"
    // 2. Вычислить сумму всех элементов массива
    // 3. Вернуть JSON с ключом "sum"

    public static String sum(String jsonSum){
        String str = jsonSum.substring(jsonSum.indexOf("[") + 1, jsonSum.indexOf("]"));
        String arr[] = str.split(",");

        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res += Integer.parseInt((arr[i].trim()));
        }
        return "{\"sum\": " + res + "}";
    }

    // TODO: Реализовать метод sumif
    // 1. Принять JSON с массивами "numbers" и "conditions" (boolean)
    // 2. Просуммировать только те элементы "numbers", где "conditions" равно true
    // 3. Вернуть JSON с ключом "sum"

    public static String sumif(String jsonSum_2){
        String numbersStr = jsonSum_2.substring(jsonSum_2.indexOf("numbers") + 10, jsonSum_2.indexOf("]", jsonSum_2.indexOf("numbers")) + 1);
        numbersStr = numbersStr.substring(1, numbersStr.length() - 1);
        String[] numStr = numbersStr.split(",");

        String conditionsStr = jsonSum_2.substring(jsonSum_2.indexOf("conditions") + 13, jsonSum_2.indexOf("]", jsonSum_2.indexOf("conditions")) + 1);
        conditionsStr = conditionsStr.substring(1, conditionsStr.length() - 1);
        String[] condStr = conditionsStr.split(",");

        int res = 0;
        for (int i = 0; i < numStr.length && i < condStr.length; i++) {
            int num = Integer.parseInt(numStr[i].trim());
            boolean cond = Boolean.parseBoolean(condStr[i].trim());
            if (cond){
                res += num;
            }
        }
        return "{\"sum_2\": " + res + "}";
    }
}
