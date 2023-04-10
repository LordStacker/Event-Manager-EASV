package dk.easv.gui.models;

import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.bll.LogicManager;
import javafx.collections.ObservableList;

public class UserModel {

    private LogicManager bll = new LogicManager();


    public User checkUserLog(String username, String password){
        return bll.checkUserLog(username, password);
    }

    public int createUser(User user){
        return bll.createUser(user);
    }

    public int deleteUser(int id){
        return bll.deleteUser(id);
    }

    public ObservableList<User> usersPlanners(Roles roles){
        return bll.usersPlanners(roles);
    }
}
