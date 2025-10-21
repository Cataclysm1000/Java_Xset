package lesson3;

public class Cookie extends Sweets {
    private String crunches;

    public Cookie(String name, double weight, double price, String crunches){
        super(name, weight, price);
        this.crunches = crunches;
    }
    public String getUnique(){
        return "Звук: " + crunches;
    }
}
