package discountex;

public class DiscountChain implements Discount {
    private final Discount[] discounts;

    public DiscountChain(Discount[] discounts){
        this.discounts = discounts;
    }

    @Override
    public double apply(double subtotal){
        double current = subtotal;
        for(Discount d: discounts){
            current = d.apply(current);
        }
        return current;
    }
}
