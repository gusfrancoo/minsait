package com.minsait.user_api.utils.OrderRefactor;

public class OrderDemo {
    public static void main(String[] args) {
        Order order = new Order();
        order.addItem(new Item("Cadeira Gamer", 1500.0));
        order.addItem(new Item("Monitor 27\"", 1200.0));
        order.addItem(new Item("Mouse", 250.0));

        TotalCalculator calculator = new OrderCalculator();
        double total = calculator.calculate(order);

        System.out.println("Total do pedido: R$" + total);
    }
}