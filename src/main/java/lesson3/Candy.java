package lesson3;

public class Candy extends Sweets {
    private String salt;

    public Candy (String name, double weight, double price, String salt){
        super(name, weight, price);
        this.salt = salt;
    }
    public String getUnique(){
        return "Вкус: " + salt;
    }
}
