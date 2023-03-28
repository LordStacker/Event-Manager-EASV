package dk.easv.gui.models;

import dk.easv.be.Ticket;
import dk.easv.be.TicketType;
import dk.easv.bll.LogicManager;
import javafx.scene.image.Image;

public class TicketViewModel {
    private LogicManager bll = new LogicManager();

    public Image getTicketImage(Ticket ticket, int eventId) {
        return bll.generateTicketImage(ticket, eventId);
    }
}
