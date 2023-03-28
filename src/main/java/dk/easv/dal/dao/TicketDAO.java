package dk.easv.dal.dao;

import dk.easv.be.Ticket;
import dk.easv.be.TicketType;
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
                if (ticketTypes.get(ticket.getTicketType()) == null && ticket.getTicketTypeId() == 0){
                    ps1.setString(1, ticket.getTicketType());
                    ps1.setDouble(2, ticket.getPrice());
                    ps1.setInt(3, eventId);
                    ResultSet rs = ps1.executeQuery();
                    rs.next();
                    ticketTypes.put(ticket.getTicketType(), rs.getInt(1));
                }
                ps.setString(1, ticket.getTicketID().toString());
                ps.setInt(2, ticket.getTicketNumber());
                if (ticket.getTicketTypeId() == 0) {
                    ps.setInt(3, ticketTypes.get(ticket.getTicketType()));
                } else {
                    ps.setInt(3, ticket.getTicketTypeId());
                }
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
            PreparedStatement ps = con.prepareStatement("SELECT Tickets.ticket_UUID, Tickets.ticket_number, ticket_type.ticket_type, ticket_type.ticket_price, ticket_type.type_id\n" +
                    "FROM Tickets INNER JOIN ticket_type ON Tickets.ticket_type_id = ticket_type.type_id\n" +
                    "WHERE ticket_type.event_id = ? ORDER BY Tickets.ticket_number");
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UUID ticketID = UUID.fromString(rs.getString("ticket_UUID"));
                int ticketNumber = rs.getInt("ticket_number");
                String ticketType = rs.getString("ticket_type");
                int typeId = rs.getInt("type_id");
                double price = rs.getDouble("ticket_price");
                Ticket ticket = new Ticket(ticketID, ticketType, ticketNumber, price, typeId);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public List<TicketType> getTicketTypes(int eventId){
        List<TicketType> ticketTypes = new ArrayList<>();
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT ticket_price, ticket_type, type_id, COUNT(ticket_UUID) AS TicketVolume FROM ticket_type INNER JOIN Tickets ON ticket_type.type_id = Tickets.ticket_type_id WHERE event_id = ? GROUP BY ticket_price, ticket_type, type_id");
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("type_id");
                String type = rs.getString("ticket_type");
                double price = rs.getDouble("ticket_price");
                int volume = rs.getInt("TicketVolume");
                TicketType ticketType = new TicketType(id, type, price, volume);
                ticketTypes.add(ticketType);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketTypes;
    }

    public void assignTicketToCustomer(String name, String email, Ticket ticket) {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Customer WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int customerId = rs.getInt("id");
                PreparedStatement ps1 = con.prepareStatement("UPDATE Tickets SET customer_id = ? WHERE ticket_UUID = ?");
                ps1.setInt(1, customerId);
                ps1.setString(2, ticket.getTicketID().toString());
                ps1.executeUpdate();
            } else {
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Customer(name, email) OUTPUT inserted.id VALUES (?, ?)");
                ps2.setString(1, name);
                ps2.setString(2, email);
                ResultSet rs1 = ps2.executeQuery();
                rs1.next();
                int customerId = rs1.getInt(1);
                PreparedStatement ps3 = con.prepareStatement("UPDATE Tickets SET customer_id = ? WHERE ticket_UUID = ?");
                ps3.setInt(1, customerId);
                ps3.setString(2, ticket.getTicketID().toString());
                ps3.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
