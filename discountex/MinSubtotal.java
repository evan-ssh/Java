package discountex;

public class MinSubtotal implements Discount {
    private final double minimum;
    private final Discount inner;

    public MinSubtotal(double minimum, Discount inner){
        this.minimum = minimum;
        this.inner = inner;
    }

    @Override
    public double apply(double subtotal){
        if(subtotal < minimum){
            return subtotal;
        }
        return inner.apply(subtotal);
    }
}
