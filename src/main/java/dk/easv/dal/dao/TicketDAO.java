package dk.easv.dal.dao;

import dk.easv.be.Ticket;
import dk.easv.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TicketDAO {
    private ConnectionManager cm = new ConnectionManager();
    public void addTickets(List<Ticket> tickets, int eventId){
        try (Connection con = cm.getConnection()){
            HashMap<String, Integer> ticketTypes = new HashMap<>();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Tickets (ticket_UUID, ticket_number, ticket_type_id) VALUES (?, ?, ?)");
            PreparedStatement ps1 = con.prepareStatement("INSERT INTO ticket_type(ticket_type, ticket_price, event_id) OUTPUT inserted.type_id VALUES (?, ?, ?)");
            for (Ticket ticket : tickets){
                if (ticketTypes.get(ticket.getTicketType()) == null){
                    ps1.setString(1, ticket.getTicketType());
                    ps1.setDouble(2, ticket.getPrice());
                    ps1.setInt(3, eventId);
                    ResultSet rs = ps1.executeQuery();
                    rs.next();
                    ticketTypes.put(ticket.getTicketType(), rs.getInt(1));
                }
                ps.setString(1, ticket.getTicketID().toString());
                ps.setInt(2, ticket.getTicketNumber());
                ps.setInt(3, ticketTypes.get(ticket.getTicketType()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> getAllTickets(int eventId){
        List<Ticket> tickets = new ArrayList<>();
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT Tickets.ticket_UUID, Tickets.ticket_number, ticket_type.ticket_type, ticket_type.ticket_price\n" +
                    "FROM Tickets INNER JOIN ticket_type ON Tickets.ticket_type_id = ticket_type.type_id\n" +
                    "WHERE ticket_type.event_id = ?");
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UUID ticketID = UUID.fromString(rs.getString("ticket_UUID"));
                int ticketNumber = rs.getInt("ticket_number");
                String ticketType = rs.getString("ticket_type");
                double price = rs.getDouble("ticket_price");
                Ticket ticket = new Ticket(ticketID, ticketType, ticketNumber, price);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }
}
