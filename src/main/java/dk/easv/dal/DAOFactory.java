package dk.easv.dal;

import dk.easv.dal.dao.CustomerDAO;
import dk.easv.dal.dao.EventDAO;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.dal.dao.UserDAO;

public class DAOFactory {
    public static IDAO getDAO(DataAccessObjects dao) {
        return switch (dao) {
            case CUSTOMER_DAO -> new CustomerDAO();
            case EVENT_DAO -> new EventDAO();
            case TICKET_DAO -> new TicketDAO();
            case USER_DAO -> new UserDAO();
        };
    }
}
