package com.minsait.user_api.utils.OrderRefactor;

public class OrderCalculator implements TotalCalculator {

    @Override
    public double calculate(Order order) {
        double total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice();
        }
        return total;
    }
}