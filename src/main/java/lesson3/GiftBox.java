package lesson3;

import java.util.ArrayList;
import java.util.List;

public class GiftBox implements Box {
    private List<Sweets> sweets = new ArrayList<>();

    @Override
    public void addSweet(Sweets sweet) {
        sweets.add(sweet);
    }

    @Override
    public void removeLast() {
        if (!sweets.isEmpty()) {
            sweets.remove(sweets.size() - 1);
        }
    }

    @Override
    public double getTotalWeight() {
        double totalWeight = 0;
        for (Sweets sweet : sweets) {
            totalWeight += sweet.getWeight();
        }
        return totalWeight;
    }

    @Override
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Sweets sweet : sweets) {
            totalPrice += sweet.getPrice();
        }
        return totalPrice;
    }

    @Override
    public void printAll() {
        System.out.println(sweets);
    }

    @Override
    public double optimizeWeight(double maxWeight) {
        while (getTotalWeight() > maxWeight && !sweets.isEmpty()) {
            Sweets lightweight = sweets.get(0);
            for (Sweets sweet : sweets) {
                if (sweet.getWeight() < lightweight.getWeight()) {
                    lightweight = sweet;
                }
            }
            sweets.remove(lightweight);
        }
        return maxWeight;
    }

    @Override
    public double optimizePrice(double maxWeight) {
        while (getTotalWeight() > maxWeight && !sweets.isEmpty()) {
            Sweets cheap = sweets.get(0);
            for (Sweets sweet : sweets) {
                if (sweet.getPrice() < cheap.getPrice()) {
                    cheap = sweet;
                }
            }
            sweets.remove(cheap);
        }
        return maxWeight;
    }
}
