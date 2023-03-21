package dk.easv.gui.models;

import dk.easv.be.Event;
import dk.easv.bll.LogicManager;

import java.time.LocalDate;

public class EventModel {
    private LogicManager bll = new LogicManager();
    public void addEvent(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes, int numberOfTickets) {
        int eventId = bll.addEvent(new Event(name, location, startDate, endDate, directions, extraNotes));
        addTickets(numberOfTickets, eventId);
    }

    public void addTickets(int numberOfTickets, int eventId) {
        bll.addTickets(numberOfTickets, eventId);
    }
}
