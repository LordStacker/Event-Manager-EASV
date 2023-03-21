package dk.easv.gui.models;

import dk.easv.bll.LogicManager;

import java.time.LocalDate;

public class EventModel {
    private LogicManager bll = new LogicManager();
    public void addEvent(String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes) {
        int numberOfTickets = 100;
        bll.addTickets(numberOfTickets);
    }

    public void addTickets(int numberOfTickets) {
        bll.addTickets(numberOfTickets);
    }
}
