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


    public void addEvent(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes, int numberOfTickets) {
        int eventId = bll.addEvent(new Event(name, location, startDate, endDate, directions, extraNotes));
        addTickets(numberOfTickets, eventId);
        getAllEvents();
    }

    public void addTickets(int numberOfTickets, int eventId) {
        bll.addTickets(numberOfTickets, eventId);
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
