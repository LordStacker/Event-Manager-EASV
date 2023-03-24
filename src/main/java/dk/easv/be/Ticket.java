package dk.easv.be;

import javafx.scene.image.Image;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.UUID;

public class Ticket { //I actually want to extend user as well

    private UUID ticketID;
    private String ticketType;
    private int ticketNumber;
    private int eventId;
    private double price;
    private int ticketTypeId;

    public Ticket(String ticketType, int ticketNumber, double price, int ticketTypeId) {
        this.ticketType = ticketType;
        this.ticketNumber = ticketNumber;
        ticketID = UUID.randomUUID();
        this.price = price;
        this.ticketTypeId = ticketTypeId;
    }


    public Ticket(UUID ticketID, String ticketType, int ticketNumber, double price, int ticketTypeId) {
        this.ticketType = ticketType;
        this.ticketNumber = ticketNumber;
        this.ticketID = ticketID;
        this.price = price;
        this.ticketTypeId = ticketTypeId;
    }

    public UUID getTicketID() {
        return ticketID;
    }

    public void setTicketID(UUID ticketID) {
        this.ticketID = ticketID;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(int ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }
}

