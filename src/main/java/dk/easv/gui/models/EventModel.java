package dk.easv.gui.models;

import dk.easv.be.Event;
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


    public int addEvent(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes) {
        int eventId = bll.addEvent(new Event(name, location, startDate, endDate, directions, extraNotes));
        getAllEvents();
        return eventId;
    }

    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets) {
        bll.addTickets(eventId, ticketType, price, numberOfTickets);
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
}
