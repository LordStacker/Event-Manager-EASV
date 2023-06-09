package dk.easv.gui.models;

import dk.easv.be.Event;

import dk.easv.be.Ticket;

import dk.easv.be.TicketType;

import dk.easv.bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public int deleteEvent(int id){
        int rowsAffected = bll.deleteEvent(id);
        getAllEvents();
        return rowsAffected;
    }

    public int addEvent(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes, String eventStartField, String eventEndFieldText) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.parse(eventStartField));
        LocalDateTime endDateTime = null;
        if (!eventEndFieldText.isEmpty())
             endDateTime = LocalDateTime.of(endDate, LocalTime.parse(eventEndFieldText));
        return bll.addEvent(new Event(name, location, startDateTime, endDateTime, directions, extraNotes));
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
            if (event.getEventStartDate().isBefore(LocalDateTime.now())) {
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

    public List<TicketType> getTicketTypes(int eventID) {
        return bll.getTicketTypes(eventID);
    }

    public void editEvent(int eventId, String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes, String startTextField, String eventEndFieldText) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.parse(startTextField));
        LocalDateTime endDateTime = null;
        if (!eventEndFieldText.isEmpty())
            endDateTime = LocalDateTime.of(endDate, LocalTime.parse(eventEndFieldText));
        bll.editEvent(eventId, name, location, startDateTime, endDateTime, directions, extraNotes);
    }

}
