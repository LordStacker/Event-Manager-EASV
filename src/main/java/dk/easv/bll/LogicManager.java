package dk.easv.bll;

import dk.easv.Main;
import dk.easv.be.Event;
import dk.easv.be.Ticket;
import dk.easv.be.TicketType;
import dk.easv.be.User;
import dk.easv.dal.dao.EventDAO;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.dal.dao.UserDAO;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LogicManager {
    private final TicketDAO ticketDAO = new TicketDAO();
    private final EventDAO eventDAO = new EventDAO();
    private final UserDAO userDAO = new UserDAO();
    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets) {
        addTickets(eventId, ticketType, price, numberOfTickets, 1, 0);
    }
    public void addTickets(int eventId, String ticketType, double price, int numberOfTickets, int startingNumber, int ticketTypeId) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = startingNumber; i <= numberOfTickets; i++) {
            tickets.add(new Ticket(ticketType, i, price, ticketTypeId));
        }

        ticketDAO.addTickets(tickets, eventId);
    }

    public List<User> checkUserLog(String username, String password){
        return userDAO.checkUserLog(username,password);
    }

    public int addEvent(Event event) {
        return eventDAO.createEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public int deleteEvent(int id) {
        return eventDAO.deleteEvent(id);
    }

    public List<Ticket> getAllTickets(int eventId) {
        return ticketDAO.getAllTickets(eventId);
    }

    public List<TicketType> getTicketTypes(int eventId) {
        return ticketDAO.getTicketTypes(eventId);
    }

    public void editEvent(int eventId, String name, String location, LocalDate startDate, LocalDate endDate, String directions, String extraNotes) {
        eventDAO.updateEvent(new Event(eventId, name, location, startDate, endDate, directions, extraNotes));
    }

    public Image generateTicketImage(Ticket ticket) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(Main.class.getResource("ticket.png")).openStream());
            int width = image.getWidth();
            int height = image.getHeight();

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics.setColor(Color.BLACK);
            graphics.drawString(ticket.getTicketType(), 100, 100);

            ImageIO.write(bufferedImage, "png", new File("src/main/resources/dk/easv/tmp/tmp-ticket.png"));

            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
