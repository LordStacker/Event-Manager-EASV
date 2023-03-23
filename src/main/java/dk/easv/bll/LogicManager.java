package dk.easv.bll;

import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.dal.dao.EventDAO;
import dk.easv.dal.dao.TicketDAO;

import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private final TicketDAO ticketDAO = new TicketDAO();
    private final EventDAO eventDAO = new EventDAO();
    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets, int startingNumber) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = startingNumber; i <= numberOfTickets; i++) {
            tickets.add(new Ticket(ticketType, i, price));
        }

        ticketDAO.addTickets(tickets, eventId);
    }

    public int addEvent(Event event) {
        return eventDAO.createEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public int deleteEvent(int id) {
        return eventDAO.deleteEvent(id);
    }
}
