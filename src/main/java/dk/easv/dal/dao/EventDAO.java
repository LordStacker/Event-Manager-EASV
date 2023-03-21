package dk.easv.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.be.Event;
import dk.easv.dal.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private final ConnectionManager cm = new ConnectionManager();

    public int createEvent(Event event) {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Event  (event_name, event_description, event_start_date, " +
                    "event_end_date, event_location, event_guidance) OUTPUT inserted.id VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventNotes());
            ps.setDate(3, Date.valueOf(event.getEventStartDate()));
            ps.setDate(4, event.getEventEndDate() == null ? null : Date.valueOf(event.getEventEndDate()));
            ps.setString(5, event.getEventLocation());
            ps.setString(6, event.getEventGuidance());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Event");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("event_name");
                String description = rs.getString("event_description");
                Date startDate = rs.getDate("event_start_date");
                Date endDate = rs.getDate("event_end_date");
                String location = rs.getString("event_location");
                String guidance = rs.getString("event_guidance");
                Event event = new Event(id, name, description, startDate.toLocalDate(), endDate == null ? null : endDate.toLocalDate(), location, guidance);
                events.add(event);
            }
            return events;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}