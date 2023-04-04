package dk.easv.dal.daoInterfaces;

import dk.easv.be.Event;
import dk.easv.dal.IDAO;

import java.util.List;

public interface IEventDAO extends IDAO {
    int createEvent(Event event);

    List<Event> getAllEvents();

    void updateEvent(Event event);

    int deleteEvent(int id);

    Event getEvent(int eventId);
}
