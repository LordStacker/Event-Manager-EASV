package dk.easv.be;

import javax.xml.stream.Location;
import java.time.LocalDateTime;

public class Event {

    private String eventName;
    private LocalDateTime eventStartDate;
    private String eventNotes;
    private Location eventLocation;

    //Optional information (must be supported but may not be used for all events)
    private LocalDateTime eventEndDate;
    private String eventGuidance;


}
