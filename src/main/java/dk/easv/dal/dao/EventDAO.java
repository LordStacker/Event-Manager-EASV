package dk.easv.dal.dao;

import dk.easv.be.Event;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.daoInterfaces.IEventDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDAO {
    private final ConnectionManager cm = new ConnectionManager();

    @Override
    public int createEvent(Event event) {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Event  (event_name, event_description, event_start_date, " +
                    "event_end_date, event_location, event_guidance) OUTPUT inserted.id VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventNotes());
            ps.setTimestamp(3, Timestamp.valueOf(event.getEventStartDate()));
            ps.setTimestamp(4, event.getEventEndDate() == null ? null : Timestamp.valueOf(event.getEventEndDate()));
            ps.setString(5, event.getEventLocation());
            ps.setString(6, event.getEventGuidance());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT Event.id,Event.event_name,event_description,event_location,event_start_date,event_end_date,event_guidance, COUNT(CASE WHEN ticket_UUID IS NOT NULL THEN 1 END) as TotalTickets ,COUNT(CASE WHEN customer_id IS NOT NULL THEN 1 END) AS SoldTickets " +
                    "FROM Event LEFT OUTER JOIN ticket_type ON Event.id = ticket_type.event_id " +
                    "LEFT OUTER JOIN Tickets ON ticket_type.type_id = Tickets.ticket_type_id " +
                    "GROUP BY Event.id, Event.event_name, event_description, event_location, event_start_date, event_end_date, event_guidance ORDER BY event_start_date ASC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("event_name");
                String description = rs.getString("event_description");
                Timestamp startDate = rs.getTimestamp("event_start_date");
                Timestamp endDate = rs.getTimestamp("event_end_date");
                String location = rs.getString("event_location");
                String guidance = rs.getString("event_guidance");
                int totalTickets = rs.getInt("TotalTickets");
                int soldTickets = rs.getInt("SoldTickets");
                Event event = new Event(id, name, description, startDate.toLocalDateTime(), endDate == null ? null : endDate.toLocalDateTime(), location, guidance, totalTickets, soldTickets);
                events.add(event);
            }
            return events;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEvent(Event event){
        try (Connection con = cm.getConnection()){
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            PreparedStatement ps = con.prepareStatement("UPDATE Event SET event_name = ?, event_description = ?, event_start_date = ?, event_end_date = ?, event_location = ?, event_guidance = ? WHERE id = ?");
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventNotes());
            ps.setTimestamp(3, Timestamp.valueOf(event.getEventStartDate()));
            ps.setTimestamp(4, event.getEventEndDate() == null ? null : Timestamp.valueOf(event.getEventEndDate()));
            ps.setString(5, event.getEventLocation());
            ps.setString(6, event.getEventGuidance());
            ps.setInt(7, event.getEventID());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteEvent(int id){
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM Event WHERE id = ? ");
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event getEvent(int eventId) {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT Event.id,Event.event_name,event_description,event_location,event_start_date,event_end_date,event_guidance, COUNT(CASE WHEN ticket_UUID IS NOT NULL THEN 1 END) as TotalTickets ,COUNT(CASE WHEN customer_id IS NOT NULL THEN 1 END) AS SoldTickets " +
                    "FROM Event LEFT OUTER JOIN ticket_type ON Event.id = ticket_type.event_id " +
                    "LEFT OUTER JOIN Tickets ON ticket_type.type_id = Tickets.ticket_type_id " +
                    "WHERE Event.id = ? " +
                    "GROUP BY Event.id, Event.event_name, event_description, event_location, event_start_date, event_end_date, event_guidance ORDER BY event_start_date");
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("event_name");
                String description = rs.getString("event_description");
                Timestamp startDate = rs.getTimestamp("event_start_date");
                Timestamp endDate = rs.getTimestamp("event_end_date");
                String location = rs.getString("event_location");
                String guidance = rs.getString("event_guidance");
                int totalTickets = rs.getInt("TotalTickets");
                int soldTickets = rs.getInt("SoldTickets");
                return new Event(id, name, description, startDate.toLocalDateTime(), endDate == null ? null : endDate.toLocalDateTime(), location, guidance, totalTickets, soldTickets);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}