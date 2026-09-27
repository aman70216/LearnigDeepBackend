package learningProject;
public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();

        Order o1 = new Order(1, "Laptop", 2, "NEW");
        Order o2 = new Order(2, "Phone", 5, "NEW");

        service.createOrder(o1);
        service.createOrder(o2);

        System.out.println(service.getOrderById(1));

        service.cancelOrder(1);

        for (Order o : service.listOrders()) {
            System.out.println(o);
        }
    }
}
