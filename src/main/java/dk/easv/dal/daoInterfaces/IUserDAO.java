package dk.easv.dal.daoInterfaces;

import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.dal.IDAO;
import javafx.collections.ObservableList;

import java.util.List;

public interface IUserDAO extends IDAO {
    List<User> checkUserLog(String username, String password);

    int createUser(User user);

    ObservableList<User> usersPlanners(Roles role);

    int deleteUser(int id);
}
