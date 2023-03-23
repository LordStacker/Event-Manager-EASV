package dk.easv.be;

public class TicketType {
    int id;
    String name;
    double price;
    int ticketVolume;

    public TicketType(int id, String name, double price, int ticketVolume) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ticketVolume = ticketVolume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketVolume() {
        return ticketVolume;
    }

    public void setTicketVolume(int ticketVolume) {
        this.ticketVolume = ticketVolume;
    }


}
