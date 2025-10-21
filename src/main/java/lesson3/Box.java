package lesson3;

public interface Box {
    void addSweet(Sweets sweet);

    void removeLast();

    double getTotalWeight();

    double getTotalPrice();

    void printAll();

    double optimizeWeight(double maxWeight);

    double optimizePrice(double maxWeight);
}
