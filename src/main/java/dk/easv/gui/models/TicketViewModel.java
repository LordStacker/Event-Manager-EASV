package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.be.Ticket;
import dk.easv.bll.LogicManager;
import javafx.scene.image.Image;

import java.util.UUID;

public class TicketViewModel {
    private LogicManager bll = new LogicManager();

    public Image getTicketImage(Ticket ticket, int eventId) {
        return bll.generateTicketImage(ticket, eventId);
    }

    public void assignTicketToCustomer(String name, String email, Ticket ticket) {
        bll.assignTicketToCustomer(name, email, ticket);
    }

    public void deassignTicket(UUID ticketId) {
        bll.deassignTicket(ticketId);
    }


    public Customer getCustomer(int customerId) {
        return bll.getCustomer(customerId);
    }
}
