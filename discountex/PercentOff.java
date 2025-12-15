package discountex;

public class PercentOff implements Discount{
    private final double percent;

    public PercentOff(double percent){
        this.percent = percent;
    }

    @Override
    public double apply(double subtotal){
        return subtotal * (1 - percent / 100.0);
    }
}
