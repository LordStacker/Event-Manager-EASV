package dk.easv.dal.daoInterfaces;

import dk.easv.be.Customer;
import dk.easv.dal.IDAO;

public interface ICustomerDAO extends IDAO {
    Customer getCustomer(int id);

    int createCustomer(Customer customer);
}
