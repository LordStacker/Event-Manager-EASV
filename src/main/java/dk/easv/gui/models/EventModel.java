package dk.easv.gui.models;

import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class EventModel {
    private LogicManager bll = new LogicManager();
    private final ObservableList<Event> obsFutureEvents;
    private final ObservableList<Event> obsPastEvents;
    private final ObservableList<Ticket> obsTickets;

    public EventModel() {
        obsFutureEvents = FXCollections.observableArrayList();
        obsPastEvents = FXCollections.observableArrayList();
        obsTickets = FXCollections.observableArrayList();
        getAllEvents();
    }


    public int addEvent(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes) {
        return bll.addEvent(new Event(name, location, startDate, endDate, directions, extraNotes));
    }

    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets) {
        bll.addTickets(eventId, ticketType, price, numberOfTickets, 1);
    }

    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets, int startingNumber) {
        bll.addTickets(eventId, ticketType, price, numberOfTickets, startingNumber);
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

    public void getAllTickets(int eventId){
        List<Ticket> tickets = bll.getAllTickets(eventId);
        obsTickets.clear();
        for (Ticket ticket:tickets) {
            obsTickets.add(ticket);
        }
    }

    public ObservableList<Event> getObsFutureEvents() {
        return obsFutureEvents;
    }

    public ObservableList<Event> getObsPastEvents() {
        return obsPastEvents;
    }

    public ObservableList<Ticket> getObsTickets(){return obsTickets;}
}
