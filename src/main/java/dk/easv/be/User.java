package dk.easv.be;

import javax.accessibility.AccessibleRole;

record User (Roles role, int userID, String userName, String userPassword, String userEmail){

}
