package dk.easv.bll;

import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.dal.dao.EventDAO;
import dk.easv.dal.dao.TicketDAO;

import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private TicketDAO ticketDAO = new TicketDAO();
    private EventDAO eventDAO = new EventDAO();
    public void addTickets(int numberOfTickets, int eventId) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i <= numberOfTickets; i++) {
            tickets.add(new Ticket("General", i, 100));
        }

        ticketDAO.addTickets(tickets, eventId);
    }

    public int addEvent(Event event) {
        return eventDAO.createEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }
}
