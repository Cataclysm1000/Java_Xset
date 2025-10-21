package lesson3;

public abstract class Sweets {
    protected String name;
    protected double weight;
    protected double price;

    public Sweets(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getUnique();

    public String toString() {
        return "Название: " + name + ", Вес: " + weight + " грамм" + ", Цена: " + price + " рублей " + getUnique();
    }

}
