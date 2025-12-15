package discountex;

public class FlatOff implements Discount {
    private final double amount;

    public FlatOff(double amount){
        this.amount = amount;
    }

    @Override
    public double apply(double subtotal){
        double result = subtotal - amount;
        return result < 0 ? 0 : result;
    }
}
