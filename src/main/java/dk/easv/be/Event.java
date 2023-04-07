package dk.easv.be;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {

    private int eventID;


    private String eventName;
    private LocalDateTime eventStartDate;
    private String eventNotes;
    private String eventLocation;

    //Optional information (must be supported but may not be used for all events)
    private LocalDateTime eventEndDate;
    private String eventGuidance;
    private int eventTickets;
    private int eventTicketsSold;


    public Event(String name, String location, LocalDateTime startDate, LocalDateTime endDate, String directions, String extraNotes){
        this.eventName = name;
        this.eventLocation = location;
        this.eventStartDate = startDate;
        this.eventEndDate = endDate;
        this.eventGuidance = directions;
        this.eventNotes = extraNotes;
    }

    public Event(int id, String name, String location, LocalDateTime startDate, LocalDateTime endDate, String directions, String extraNotes){
        this.eventID = id;
        this.eventName = name;
        this.eventLocation = location;
        this.eventStartDate = startDate;
        this.eventEndDate = endDate;
        this.eventGuidance = directions;
        this.eventNotes = extraNotes;
    }

    public Event(int id, String name, String location, LocalDateTime startDate, LocalDateTime endDate, String directions, String extraNotes, int tickets, int ticketsSold){
        this.eventID = id;
        this.eventName = name;
        this.eventLocation = location;
        this.eventStartDate = startDate;
        this.eventEndDate = endDate;
        this.eventGuidance = directions;
        this.eventNotes = extraNotes;
        this.eventTickets = tickets;
        this.eventTicketsSold = ticketsSold;
    }
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(LocalDateTime eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventNotes() {
        return eventNotes;
    }

    public void setEventNotes(String eventNotes) {
        this.eventNotes = eventNotes;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public LocalDateTime getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(LocalDateTime eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventGuidance() {
        return eventGuidance;
    }

    public void setEventGuidance(String eventGuidance) {
        this.eventGuidance = eventGuidance;
    }

    public int getEventTickets() {
        return eventTickets;
    }

    public void setEventTickets(int eventTickets) {
        this.eventTickets = eventTickets;
    }

    public int getEventTicketsSold() {
        return eventTicketsSold;
    }

    public void setEventTicketsSold(int eventTicketsSold) {
        this.eventTicketsSold = eventTicketsSold;
    }
}
