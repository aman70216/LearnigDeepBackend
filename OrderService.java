package learningProject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

   
    public void createOrder(Order order) {
        String sql = "INSERT INTO orders (id, product, quantity, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getId());
            ps.setString(2, order.getProduct());
            ps.setInt(3, order.getQuantity());
            ps.setString(4, order.getStatus());
            ps.executeUpdate();
            System.out.println("Order created: " + order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch order by ID
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getString("product"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cancel order
    public void cancelOrder(int id) {
        String sql = "UPDATE orders SET status = 'CANCELLED' WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Order cancelled: " + id);
            } else {
                System.out.println("Order not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // List all orders
    public List<Order> listOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("product"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
