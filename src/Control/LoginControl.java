package Control;

import Model.DBConnection;
import Model.User;
import View.LoginView;
import Model.LoginModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginControl {
    LoginView view;
    LoginModel model;
    boolean loginSucces = false;
    private User currentUser = null;
    public LoginControl(LoginView view,LoginModel model) {
        this.view = view;
        this.model = model;
        view.addEnterButtonActionListener(new ValidateActionListener());
    }

    public User waitForLogin() {
        while(!loginSucces) {
            System.out.println(" ");
        }
        return currentUser;
    }

    private class ValidateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUserName();
            String password = view.getPassword();
            User u = new User(0,username,password,0);
            if(!DBConnection.findUser(username)) {
                try {
                    currentUser = u;
                    DBConnection.addUser(u);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                loginSucces = true;
                view.close();
                return;
            }

            User user = DBConnection.getUser(username);
            if(user.getPassword().equals(password)) {
                loginSucces = true;
                currentUser = user;
                view.close();
                return;
            }
            view.wrongPasswordMessage();
        }

    }
}
