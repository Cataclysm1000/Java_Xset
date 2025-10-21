package lesson5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Collections {
    private static final int SIZE = 1000000;
    private static final int ADD = 500000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < SIZE; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        System.out.println("Добавить 500к в середину");
        addElement(arrayList, ADD, "ArrayList");
        addElement(linkedList, ADD, "LinkedList");

        System.out.println("Получить элемент из середины");
        getElement(arrayList, "Arraylist");
        getElement(linkedList, "Linkedlist");

        System.out.println("Удалить элемент из середины");
        removeElement(arrayList, "Arraylist");
        removeElement(linkedList, "Linkedlist");
    }

    static void addElement(List<Integer> list, int addCount, String name) {
        int mid = list.size() / 2;
        long start = System.currentTimeMillis();
        for (int i = 0; i < addCount; i++) {
            list.add(mid, i);
        }
        long finish = System.currentTimeMillis();
        System.out.println(name + ": " + (finish - start) + " мс");
    }

    static void getElement(List<Integer> list, String name) {
        int mid = list.size() / 2;
        long start = System.currentTimeMillis();
        int value = list.get(mid);
        long finish = System.currentTimeMillis();
        System.out.println(name + ": " + (finish - start) + " мс, " + value);
    }

    static void removeElement(List<Integer> list, String name) {
        int mid = list.size() / 2;
        long start = System.currentTimeMillis();
        list.remove(mid);
        long finish = System.currentTimeMillis();
        System.out.println(name + ": " + (finish - start) + " мс");
    }
}
