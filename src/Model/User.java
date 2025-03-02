package Model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private int score;

    public User(int id,String username,String password,int score) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.score = score;
    }

    public String getUserName() { return username; }
    public String getPassword() { return password; }
    public int getScore() { return score; }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + "\'" + ", password='" + password + "\'" + '}';
    }

}
