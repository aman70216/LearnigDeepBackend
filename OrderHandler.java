package learningProject;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

class OrderHandler implements HttpHandler {
    private OrderService orderService = new OrderService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";

        if ("GET".equals(exchange.getRequestMethod())) {
            response = orderService.listOrders().toString();
        } else if ("POST".equals(exchange.getRequestMethod())) {
            Order o = new Order(3, "Tablet", 1, "NEW");
            orderService.createOrder(o);
            response = "Order created: " + o;
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}