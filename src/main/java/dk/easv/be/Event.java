package dk.easv.be;

import javax.xml.stream.Location;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {

    private int eventID;


    private String eventName;
    private LocalDate eventStartDate;
    private String eventNotes;
    private String eventLocation;

    //Optional information (must be supported but may not be used for all events)
    private LocalDate eventEndDate;
    private String eventGuidance;


    public Event(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes){
        this.eventName = name;
        this.eventLocation = location;
        this.eventStartDate = startDate;
        this.eventEndDate = endDate;
        this.eventGuidance = directions;
        this.eventNotes = extraNotes;
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

    public LocalDate getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(LocalDate eventStartDate) {
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

    public LocalDate getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(LocalDate eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventGuidance() {
        return eventGuidance;
    }

    public void setEventGuidance(String eventGuidance) {
        this.eventGuidance = eventGuidance;
    }

}
