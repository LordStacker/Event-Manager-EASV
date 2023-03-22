package dk.easv.be;

import javax.xml.stream.Location;
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

    public Event(int eventID, String eventName, LocalDateTime eventStartDate, String eventNotes, String eventLocation, LocalDateTime eventEndDate, String eventGuidance) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventNotes = eventNotes;
        this.eventLocation = eventLocation;
        this.eventEndDate = eventEndDate;
        this.eventGuidance = eventGuidance;
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

}

