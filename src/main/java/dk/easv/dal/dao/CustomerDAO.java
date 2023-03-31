package dk.easv.dal.dao;

import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    private ConnectionManager cm = new ConnectionManager();
    public Customer getCustomer(int id) {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Customer WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                return new Customer(id, name, email);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int createCustomer(Customer customer){
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Customer  " +
                            "(name, email) " +
                            "OUTPUT inserted.id VALUES (?, ?)");
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerEmail());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
