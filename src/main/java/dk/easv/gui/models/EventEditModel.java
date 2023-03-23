package dk.easv.gui.models;

import dk.easv.be.Event;
import dk.easv.be.TicketType;
import dk.easv.bll.LogicManager;

import java.util.List;

public class EventEditModel {
    private LogicManager bll = new LogicManager();
    private List<TicketType> ticketTypes;
    private Event event;
    private List<TicketType> editedTicketTypes;

    public EventEditModel(Event event, List<TicketType> ticketTypes) {
        this.event = event;
        this.ticketTypes = ticketTypes;
    }

    public List<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<TicketType> getEditedTicketTypes() {
        return editedTicketTypes;
    }

    public void setEditedTicketTypes(List<TicketType> editedTicketTypes) {
        this.editedTicketTypes = editedTicketTypes;
    }
}
