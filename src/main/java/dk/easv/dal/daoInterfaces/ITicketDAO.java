package dk.easv.dal.daoInterfaces;

import dk.easv.be.Ticket;
import dk.easv.be.TicketType;
import dk.easv.dal.IDAO;

import java.util.List;
import java.util.UUID;

public interface ITicketDAO extends IDAO {
    void addTickets(List<Ticket> tickets, int eventId);

    List<Ticket> getAllTickets(int eventId);

    List<TicketType> getTicketTypes(int eventId);

    void assignTicketToCustomer(String name, String email, Ticket ticket);

    void deassignTicket(UUID ticketId);
}
