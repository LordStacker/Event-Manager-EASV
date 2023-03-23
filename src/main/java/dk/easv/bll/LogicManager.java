package dk.easv.bll;

import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.be.TicketType;
import dk.easv.be.User;
import dk.easv.dal.dao.EventDAO;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.dal.dao.UserDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private final TicketDAO ticketDAO = new TicketDAO();
    private final EventDAO eventDAO = new EventDAO();
    private final UserDAO userDAO = new UserDAO();
    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets) {
        addTickets(eventId, ticketType, price, numberOfTickets, 1, 0);
    }
    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets, int startingNumber, int ticketTypeId) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = startingNumber; i <= numberOfTickets; i++) {
            tickets.add(new Ticket(ticketType, i, price, ticketTypeId));
        }

        ticketDAO.addTickets(tickets, eventId);
    }

    public List<User> checkUserLog(String username, String password){
        return userDAO.checkUserLog(username,password);
    }

    public int addEvent(Event event) {
        return eventDAO.createEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public List<Ticket> getAllTickets(int eventId) {
        return ticketDAO.getAllTickets(eventId);
    }

    public List<TicketType> getTicketTypes(int eventId) {
        return ticketDAO.getTicketTypes(eventId);
    }

    public void editEvent(int eventId, String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes) {
        eventDAO.updateEvent(new Event(eventId, name, location, startDate, endDate, directions, extraNotes));
    }
}
