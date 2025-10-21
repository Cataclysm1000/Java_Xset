package lesson3;

public class Chocolate extends Sweets {
    private String nuts;

    public Chocolate(String name, double weight, double price, String nuts) {
        super(name, weight, price);
        this.nuts = nuts;
    }

    public String getUnique() {
        return "Аллерген: " + nuts;
    }
}
