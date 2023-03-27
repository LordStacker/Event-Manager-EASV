package dk.easv.gui.models;

import dk.easv.be.Ticket;
import dk.easv.be.TicketType;
import dk.easv.bll.LogicManager;
import javafx.scene.image.Image;

public class TicketViewModel {
    private Ticket ticket;
    private LogicManager bll = new LogicManager();
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Image getTicketImage() {
        return bll.generateTicketImage(ticket);
    }
}
