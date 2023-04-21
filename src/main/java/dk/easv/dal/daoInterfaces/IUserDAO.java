package dk.easv.dal.daoInterfaces;

import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.dal.IDAO;
import javafx.collections.ObservableList;

public interface IUserDAO extends IDAO {
    User checkUserLog(String username, String password);

    int createUser(User user);

    ObservableList<User> usersPlanners(Roles role);

    int deleteUser(int id);
}
