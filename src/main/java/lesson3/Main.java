package lesson3;

public class Main {
    public static void main(String[] args) {
        GiftBox box = new GiftBox();

        box.addSweet(new Candy("Meller", 50, 100, "Солёный"));
        box.addSweet(new Chocolate("Snickers", 80, 60, "Арахис"));
        box.addSweet(new Cookie("Oreo", 100, 40, "Хрустит"));

        System.out.println("Все сладости: ");
        box.printAll();

        System.out.println("Общий вес: " + box.getTotalWeight());
        System.out.println("Общая цена: " + box.getTotalPrice());

        System.out.println("Оптимизация по весу:");
        box.optimizeWeight(100);
        box.printAll();

        System.out.println("Оптимизация по цене:");
        box.optimizePrice(100);
        box.printAll();
    }
}
