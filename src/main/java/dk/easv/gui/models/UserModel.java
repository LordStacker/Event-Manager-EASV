package dk.easv.gui.models;

import dk.easv.be.Roles;
import dk.easv.be.User;
import dk.easv.bll.LogicManager;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModel {

    private LogicManager bll = new LogicManager();


    public List<User> checkUserLog(String username, String password){
        return bll.checkUserLog(username, password);
    }

    public ObservableList<User> usersPlanners(Roles roles){
        return bll.usersPlanners(roles);
    }
}
