package dk.easv.be;

public class Customer {

    private int customerID;
    private  String customerEmail;
    private String customerName;

    public Customer(int customerID, String customerEmail, String customerName){

        this.customerID=customerID;
        this.customerEmail=customerEmail;
        this.customerName=customerName;

    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
