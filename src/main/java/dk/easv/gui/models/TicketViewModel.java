package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.be.Ticket;
import dk.easv.bll.LogicManager;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

public class TicketViewModel {
    private LogicManager bll = new LogicManager();
    private BufferedImage image;
    private Ticket ticket;

    public Image getTicketImage(Ticket ticket, int eventId) {
        this.ticket = ticket;
        image = bll.generateTicketImage(ticket, eventId);;
        return SwingFXUtils.toFXImage(image, null);
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

    public void saveAsPDF(File selectedDirectory) {
        bll.generatePDFFromImage(image, selectedDirectory, ticket);
    }

    public void printTicket() {
        bll.printTicket();
    }
}
