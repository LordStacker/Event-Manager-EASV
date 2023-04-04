package dk.easv.bll;
import dk.easv.be.*;
import dk.easv.dal.dao.EventDAO;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.dal.dao.UserDAO;
import dk.easv.util.AlertHelper;
import javafx.collections.ObservableList;
import dk.easv.Main;
import dk.easv.be.Event;
import dk.easv.dal.dao.CustomerDAO;
import javafx.scene.control.Alert;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class LogicManager {
    private final TicketDAO ticketDAO = new TicketDAO();
    private final EventDAO eventDAO = new EventDAO();
    private final UserDAO userDAO = new UserDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
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

    public int createUser(User user){
        return userDAO.createUser(user);
    }

    public int deleteUser(int id){return userDAO.deleteUser(id);  }

    public ObservableList<User> usersPlanners(Roles roles){return userDAO.usersPlanners(roles); }
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

    public BufferedImage generateTicketImage(Ticket ticket, int eventId) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(Main.class.getResource("ticket.png")).openStream());
            int width = image.getWidth();
            int height = image.getHeight();

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
            graphics.drawImage(image, 0, 0, null);
            graphics.setBackground(Color.WHITE);
            graphics.setFont(new Font("Arial", Font.PLAIN, 30));
            graphics.setColor(Color.BLACK);
            Customer customer = customerDAO.getCustomer(ticket.getCustomerId());
            if (customer != null) {
                graphics.drawString(customer.getCustomerName(), 30, 40);
                graphics.drawString(customer.getCustomerEmail(), 30, 80);
            } else {
                graphics.drawString("Not assigned", 30, 40);
            }
            ByteArrayInputStream qr = new ByteArrayInputStream(QRCode.from(ticket.getTicketID().toString()).to(ImageType.PNG).withSize(400, 400).stream().toByteArray());
            image = ImageIO.read(qr);
            graphics.drawImage(image, 30, 110, null);
            graphics.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics.drawString(ticket.getTicketID().toString(), 45, 500);
            graphics.setFont(new Font("Arial", Font.PLAIN, 30));
            graphics.drawString("Ticket number: " + ticket.getTicketNumber(), 30, 580);
            graphics.drawString(ticket.getTicketType(), 30, 620);


            Event event = eventDAO.getEvent(eventId);
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Arial", Font.PLAIN, 50));
            graphics.drawString(event.getEventName(), 600, 80);
            graphics.setFont(new Font("Arial", Font.PLAIN, 30));
            graphics.drawString(event.getEventNotes(), 620, 140);

            graphics.drawString("Start date: " + event.getEventStartDate().toString(), 600, 530);
            LocalDate endDate = event.getEventEndDate();
            if (endDate != null) {
                graphics.drawString("End date: " + endDate, 600, 570);
            } else {
                graphics.drawString("End date: ", 600, 570);
            }

            graphics.drawString("Location: " + event.getEventLocation(), 1100, 530);
            graphics.drawString("Directions: " + event.getEventGuidance(), 1100, 570);

            ImageIO.write(bufferedImage, "png", new File("src/main/resources/dk/easv/tmp/tmp-ticket.png"));

            return bufferedImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generatePDFFromImage(BufferedImage bufferedImage, File selectedDirectory, Ticket ticket){
        try (PDDocument pdDocument = new PDDocument()) {
            PDRectangle ticketPage = new PDRectangle(bufferedImage.getWidth(), bufferedImage.getHeight());
            PDPage page = new PDPage(ticketPage);
            pdDocument.addPage(page);
            PDImageXObject pdImage = LosslessFactory.createFromImage(pdDocument, bufferedImage);
            try (PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.drawImage(pdImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
            }
            if(selectedDirectory != null){
                pdDocument.save(selectedDirectory.getAbsolutePath()+ "/" + ticket.getTicketID() + ".pdf");
            }
            else {
                AlertHelper.showDefaultAlert("You must choose a folder to download PDF", Alert.AlertType.WARNING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printTicket(BufferedImage image, Ticket ticket) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                double pageWidth = pageFormat.getImageableWidth();
                double pageHeight = pageFormat.getImageableHeight();
                double imageWidth = image.getWidth();
                double imageHeight = image.getHeight();

                double scaleX = pageWidth / imageWidth;
                double scaleY = pageHeight / imageHeight;
                double scale = Math.min(scaleX, scaleY);
                g2d.scale(scale, scale);

                double x = (pageWidth / scale - imageWidth) / 2;
                double y = (pageHeight / scale - imageHeight) / 2;
                g2d.drawImage(image, (int)x, (int)y, null);
                return PAGE_EXISTS;
            }
        });
        job.setJobName(ticket.getTicketID().toString());
        job.printDialog();
        job.print();
    }

    public void assignTicketToCustomer(String name, String email, Ticket ticket) {
        ticketDAO.assignTicketToCustomer(name, email, ticket);
    }

    public Customer getCustomer(int customerId) {
        return customerDAO.getCustomer(customerId);
    }

    public int createCustomer(String customerName, String customerEmail){
        return customerDAO.createCustomer(new Customer(customerName, customerEmail));
    }
    public void deassignTicket(UUID ticketId){
        ticketDAO.deassignTicket(ticketId);
    }
}
