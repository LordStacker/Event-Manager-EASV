package dk.easv.dal;

import dk.easv.dal.dao.CustomerDAO;
import dk.easv.dal.dao.EventDAO;
import dk.easv.dal.dao.TicketDAO;
import dk.easv.dal.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DAOFactoryTest {
    @DisplayName("Test getDAO with CustomerDAO")
    @Test
    void getDAO() {
        IDAO dao = DAOFactory.getDAO(DataAccessObjects.CUSTOMER_DAO);
        Assertions.assertNotNull(dao);
        Assertions.assertEquals(dao.getClass(), CustomerDAO.class);
    }

    @DisplayName("Test getDAO with EventDAO")
    @Test
    void getDAO2() {
        IDAO dao = DAOFactory.getDAO(DataAccessObjects.EVENT_DAO);
        Assertions.assertNotNull(dao);
        Assertions.assertEquals(dao.getClass(), EventDAO.class);
    }

    @DisplayName("Test getDAO with TicketDAO")
    @Test
    void getDAO3() {
        IDAO dao = DAOFactory.getDAO(DataAccessObjects.TICKET_DAO);
        Assertions.assertNotNull(dao);
        Assertions.assertEquals(dao.getClass(), TicketDAO.class);
    }

    @DisplayName("Test getDAO with UserDAO")
    @Test
    void getDAO4() {
        IDAO dao = DAOFactory.getDAO(DataAccessObjects.USER_DAO);
        Assertions.assertNotNull(dao);
        Assertions.assertEquals(dao.getClass(), UserDAO.class);
    }

    @DisplayName("Test getDAO with null")
    @Test
    void getDAO5() {
        Assertions.assertThrows(NullPointerException.class, () -> DAOFactory.getDAO(null));
    }
}