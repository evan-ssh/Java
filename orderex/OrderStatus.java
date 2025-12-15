package orderex;


public enum OrderStatus {
    NEW,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELED;

    public OrderStatus next (Action a){
        if(this == DELIVERED || this == CANCELED){
            throw new IllegalStateException(this + " cannot perform " + a);
        }


        if( a == Action.CANCEL){
            return CANCELED;
        }

        switch (this) {
            case NEW:
                if(a == Action.PAID){
                    return PAID;
                    break;
                }
            case PAID:
                if(a == Action.SHIP){
                    return SHIPPED;
                    break;
                }
            case SHIPPED:
                if(a == Action.DELIVER){
                    return DELIVERED;
                    break;
                }
            default:
                break;
        }
        throw new IllegalStateException(this + " cannot perform " + a);
    }

    public static OrderStatus applyActions(OrderStatus start, Action[] actions ){
        OrderStatus cur = start;
        for(Action a : actions){
            cur = cur.next(a);
        }
        return cur;
    }
    
}
