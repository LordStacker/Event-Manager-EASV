package dk.easv.gui.models;

import dk.easv.be.Event;
import dk.easv.be.TicketType;
import dk.easv.bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class EventModel {
    private LogicManager bll = new LogicManager();
    private final ObservableList<Event> obsFutureEvents;
    private final ObservableList<Event> obsPastEvents;

    public EventModel() {
        obsFutureEvents = FXCollections.observableArrayList();
        obsPastEvents = FXCollections.observableArrayList();
        getAllEvents();
    }

    public int deleteEvent(int id){
        int rowsAffected = bll.deleteEvent(id);
        getAllEvents();
        return rowsAffected;
    }

    public int addEvent(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes) {
        return bll.addEvent(new Event(name, location, startDate, endDate, directions, extraNotes));
    }

    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets) {
        bll.addTickets(eventId, ticketType, price, numberOfTickets);
    }

    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets, int startingNumber, int ticketTypeId) {
        bll.addTickets(eventId, ticketType, price, numberOfTickets, startingNumber, ticketTypeId);
    }

    public void getAllEvents() {
        List<Event> events = bll.getAllEvents();
        obsPastEvents.clear();
        obsFutureEvents.clear();
        for (Event event : events) {
            if (event.getEventStartDate().isBefore(LocalDate.now())) {
                obsPastEvents.add(event);
            } else {
                obsFutureEvents.add(event);
            }
        }
    }

    public ObservableList<Event> getObsFutureEvents() {
        return obsFutureEvents;
    }

    public ObservableList<Event> getObsPastEvents() {
        return obsPastEvents;
    }

    public List<TicketType> getTicketTypes(int eventID) {
        return bll.getTicketTypes(eventID);
    }

    public void editEvent(int eventId, String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes) {
        bll.editEvent(eventId, name, location, startDate, endDate, directions, extraNotes);
    }
}
