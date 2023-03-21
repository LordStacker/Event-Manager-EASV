package dk.easv.bll;

import dk.easv.be.Ticket;
import dk.easv.dal.dao.TicketDAO;

import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private TicketDAO ticketDAO = new TicketDAO();
    public void addTickets(int numberOfTickets) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < numberOfTickets; i++) {
            tickets.add(new Ticket("General", i, 100));
        }

        ticketDAO.addTicket(tickets);
    }
}
