package dk.easv.be;

import javafx.scene.image.Image;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class Ticket extends Event {

    private int ticketID;
    private String eventName;
    private int amountOfTicket;

    public Ticket(int eventID, String eventName, LocalDateTime eventStartDate, String eventNotes, String eventLocation, LocalDateTime eventEndDate, String eventGuidance) {
        super(eventID, eventName, eventStartDate, eventNotes, eventLocation, eventEndDate, eventGuidance);
    }

    




}



