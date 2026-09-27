package learningProject;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import com.google.gson.Gson;
import java.io.InputStream;

class OrderHandler implements HttpHandler {
    private OrderService orderService = new OrderService();
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";

        if ("POST".equals(exchange.getRequestMethod())) {
            // Read request body
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes());
            
            // Convert JSON → Order object
            Order order = gson.fromJson(body, Order.class);
            orderService.createOrder(order);

            response = "Order created: " + order;
        } else if ("GET".equals(exchange.getRequestMethod())) {
            response = gson.toJson(orderService.listOrders());
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
