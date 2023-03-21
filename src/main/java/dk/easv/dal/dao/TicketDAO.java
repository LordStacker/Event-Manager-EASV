package dk.easv.dal.dao;

import dk.easv.be.Ticket;
import dk.easv.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class TicketDAO {
    private ConnectionManager cm = new ConnectionManager();
    public void addTicket(List<Ticket> tickets) {
        try (Connection con = cm.getConnection()){
            HashMap<String, Integer> ticketTypes = new HashMap<>();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Tickets (ticket_UUID, ticket_number, event_id, ticket_type_id) VALUES (?, ?, ?, ?)");
            PreparedStatement ps1 = con.prepareStatement("INSERT INTO ticket_type(ticket_type, ticket_price, event_id) OUTPUT inserted.type_id VALUES (?, ?, ?)");
            for (Ticket ticket : tickets){
                if (ticketTypes.get(ticket.getTicketType()) == null){
                    ps1.setString(1, ticket.getTicketType());
                    ps1.setDouble(2, ticket.getPrice());
                    ps1.setInt(3, 2);
                    ResultSet rs = ps1.executeQuery();
                    rs.next();
                    ticketTypes.put(ticket.getTicketType(), rs.getInt(1));
                }
                ps.setString(1, ticket.getTicketID().toString());
                ps.setInt(2, ticket.getTicketNumber());
                ps.setInt(3, 2);
                ps.setInt(4, ticketTypes.get(ticket.getTicketType()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
